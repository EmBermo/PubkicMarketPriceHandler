package es.bermo.emilio;

import es.bermo.emilio.dto.ClientPriceDto;
import es.bermo.emilio.subscriber.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SubsciberTests {
    static final Logger logger = Logger.getLogger(SubsciberTests.class.getName());

    @Test
    public void testSubscriber1(){
        try {
            TextMessage message = mock(TextMessage.class);
            Mockito.when(message.getText()).thenReturn("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");
            Subscriber subscriber = new Subscriber.Builder(null).build();
            subscriber.onMessage(message);
            ClientPriceDto result = subscriber.getSubscriberResult();
            assertEquals(106, result.getId());
            assertEquals("EUR/USD", result.getCurrencies());
            assertEquals(new BigDecimal("0.99000"), result.getBid());
            assertEquals(new BigDecimal("1.32000"), result.getAsk());
        } catch (JMSException | NamingException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void testSubscriber2(){
        try {
            TextMessage message = mock(TextMessage.class);
            Mockito.when(message.getText()).thenReturn("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
            Subscriber subscriber = new Subscriber.Builder(null).build();
            subscriber.onMessage(message);
            ClientPriceDto result = subscriber.getSubscriberResult();
            assertEquals(107, result.getId());
            assertEquals("EUR/JPY", result.getCurrencies());
            assertEquals(new BigDecimal("107.640"), result.getBid());
            assertEquals(new BigDecimal("131.890"), result.getAsk());
        } catch (JMSException | NamingException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testSubscriber3(){
        try {
            TextMessage message = mock(TextMessage.class);
            Mockito.when(message.getText()).thenReturn("108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002");
            Subscriber subscriber = new Subscriber.Builder(null).build();
            subscriber.onMessage(message);
            ClientPriceDto result = subscriber.getSubscriberResult();
            assertEquals(108, result.getId());
            assertEquals("GBP/USD", result.getCurrencies());
            assertEquals(new BigDecimal("1.12500"), result.getBid());
            assertEquals(new BigDecimal("1.38160"), result.getAsk());
        } catch (JMSException | NamingException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testSubscriber4(){
        try {
            TextMessage message = mock(TextMessage.class);
            Mockito.when(message.getText()).thenReturn("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100");
            Subscriber subscriber = new Subscriber.Builder(null).build();
            subscriber.onMessage(message);
            ClientPriceDto result = subscriber.getSubscriberResult();
            assertEquals(109, result.getId());
            assertEquals("GBP/USD", result.getCurrencies());
            assertEquals(new BigDecimal("1.12491"), result.getBid());
            assertEquals(new BigDecimal("1.38171"), result.getAsk());
        } catch (JMSException | NamingException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void testSubscriber5(){
        try {
            TextMessage message = mock(TextMessage.class);
            Mockito.when(message.getText()).thenReturn("110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110");
            Subscriber subscriber = new Subscriber.Builder(null).build();
            subscriber.onMessage(message);
            ClientPriceDto result = subscriber.getSubscriberResult();
            assertEquals(110, result.getId());
            assertEquals("EUR/JPY", result.getCurrencies());
            assertEquals(new BigDecimal("107.649"), result.getBid());
            assertEquals(new BigDecimal("131.901"), result.getAsk());
            logger.log(Level.INFO, result.toString());
        } catch (JMSException | NamingException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

}
