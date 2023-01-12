package es.bermo.emilio.util;

public interface Config {
    String FILE_PROPERTIES_NAME = "config.properties";
    String CONNECTION_FACTORY_NAME="connection.factory.name";
    String USER_NAME = "user.name";
    String USER_CREDENTIAL = "user.credential";
    String TOPIC_NAME = "topic.name";
    String SUBSCRIPTION_NAME = "subscription.name";

    String DATE_FORMAT_MPF = "dd-MM-yyyy HH:mm:ss:SSS";
    String CSV_SEPARATOR = ",";
    String COMMISSION = "0.1D";
}
