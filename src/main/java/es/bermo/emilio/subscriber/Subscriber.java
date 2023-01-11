package es.bermo.emilio.subscriber;

import es.bermo.emilio.dto.ClientPriceDto;
import es.bermo.emilio.util.ClientPriceFromCSVString;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Subscriber implements MessageListener {
    private static final Logger logger = Logger.getLogger(Subscriber.class.getName());
    private final Properties env;

    private final InitialContext jndi;


    private final TopicConnectionFactory factory;


    private final TopicConnection connection;
    private final TopicSession session;
    private final Topic fxTopic;
    private final TopicSubscriber topicSubscriber;
    private ClientPriceDto result;


    private Subscriber(Builder builder){
        env = builder.env;
        jndi = builder.jndi;
        factory = builder.factory;
        connection = builder.connection;
        session = builder.session;
        fxTopic = builder.fxTopic;
        topicSubscriber = builder.topicSubscriber;
    }

    public static class Builder {
        private Properties env;

        private InitialContext jndi;


        private TopicConnectionFactory factory;

        private TopicConnection connection;
        private TopicSession session;
        private Topic fxTopic;
        private TopicSubscriber topicSubscriber;

        public Builder(Properties env) {
            this.env = env;
        }

        public Builder withInitialContext(InitialContext jndi) {
            this.jndi = jndi;
            return this;
        }

        public Builder withTopicConnectionFactory(String connectionFactoryName) throws NamingException {
            this.factory = (TopicConnectionFactory) jndi.lookup(connectionFactoryName);
            return this;
        }

        public Builder withUserAndPassword(String user, String pass) throws JMSException {
            this.connection = factory.createTopicConnection(user, pass);
            this.connection.start();
            return this;
        }

        public Builder withSession(boolean transacted, int acknowledgeMode) throws JMSException {
            this.session = connection.createTopicSession(transacted, acknowledgeMode);
            return this;
        }

        public Builder withTopic(String topicName) throws NamingException {
            this.fxTopic = (Topic) jndi.lookup(topicName);
            return this;
        }

        public Builder withTopicSubscriber(String subscriptionName) throws JMSException {
            this.topicSubscriber = session.createDurableSubscriber(fxTopic, subscriptionName);
            return this;
        }

        public Subscriber build() throws NamingException, JMSException {
            return new Subscriber(this);
        }
    }


    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        String text = "";
        try {
            text = textMessage.getText();
        } catch (JMSException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
        ClientPriceFromCSVString clientPriceFromCSVString = new ClientPriceFromCSVString.Builder().build();
        result = clientPriceFromCSVString.getFromCSVString(text);
    }

    public ClientPriceDto getSubscriberResult() {
        return result;
    }

    public void close() throws JMSException {
        connection.close();
    }
}
