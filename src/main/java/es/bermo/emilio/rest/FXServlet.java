package es.bermo.emilio.rest;

import es.bermo.emilio.subscriber.Subscriber;
import es.bermo.emilio.util.Config;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "FXServlet",
        urlPatterns = "/fx-prices")
public class FXServlet {

    private static final Logger logger = Logger.getLogger(FXServlet.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        res.setContentType(MediaType.TEXT_PLAIN);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.getWriter().write(getSubscriberResult());
    }

    private static String getSubscriberResult() {
        Properties env = new Properties();
        try (InputStream input = FXServlet.class.getClassLoader().getResourceAsStream(Config.FILE_PROPERTIES_NAME)) {
            env.load(input);
        }catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        Subscriber subscriber = null;
        try {
            subscriber = new Subscriber.Builder(env)
                    .withInitialContext(new InitialContext(env))
                    .withTopicConnectionFactory(env.getProperty(Config.CONNECTION_FACTORY_NAME))
                    .withUserAndPassword(env.getProperty(Config.USER_NAME), env.getProperty(Config.USER_CREDENTIAL))
                    .withSession(false, Session.AUTO_ACKNOWLEDGE)
                    .withTopic(env.getProperty(Config.TOPIC_NAME))
                    .withTopicSubscriber(env.getProperty(Config.SUBSCRIPTION_NAME))
                    .build();
            logger.log(Level.INFO, subscriber.getSubscriberResult().toString());
        } catch (NamingException | JMSException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if (subscriber != null) {
                    subscriber.close();
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        return  subscriber != null ? subscriber.getSubscriberResult().toString() : "";
    }
}
