package es.bermo.emilio.subscriber;

import es.bermo.emilio.dto.ClientPriceDto;
import es.bermo.emilio.util.ClientPriceFromCSVString;
import es.bermo.emilio.util.FXPair;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
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
    private Map<FXPair,ClientPriceDto> result = new HashMap<>();


    private Subscriber(Builder builder){
        this.env = builder.env;
        this.jndi = builder.jndi;
        this.factory = builder.factory;
        this.connection = builder.connection;
        this.session = builder.session;
        this.fxTopic = builder.fxTopic;
        this.topicSubscriber = builder.topicSubscriber;
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
            Scanner scanner = new Scanner(text);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ClientPriceFromCSVString clientPriceFromCSVString = new ClientPriceFromCSVString.Builder().build();
                ClientPriceDto value = clientPriceFromCSVString.getFromCSVString(line);
                FXPair key = FXPair.switchCurrencies(value.getCurrencies());
                if(!result.containsKey(key) || result.get(key).getZonedDateTime().isBefore(value.getZonedDateTime())){
                    result.put(key,value);
                }
            }
            scanner.close();
        } catch (JMSException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<FXPair,ClientPriceDto> getSubscriberResult() {
        return result;
    }

    public void close() throws JMSException {
        connection.close();
    }
}
