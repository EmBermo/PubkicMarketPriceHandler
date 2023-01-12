package es.bermo.emilio.util;

import es.bermo.emilio.dto.ClientPriceDto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ClientPriceFromCSVString {

    private final String dateFormat;
    private final String csvSeparator;
    private final double commission;

    private ClientPriceFromCSVString(Builder builder){
        dateFormat = builder.dateFormat;
        csvSeparator = builder.csvSeparator;
        commission = builder.commission;

    }
    public static class Builder {
        private String dateFormat = Config.DATE_FORMAT_MPF;
        private String csvSeparator = Config.CSV_SEPARATOR;
        private double commission = Double.parseDouble(Config.COMMISSION);

        public Builder withDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        public Builder withCsvSeparator(String csvSeparator) {
            this.csvSeparator = csvSeparator;
            return this;
        }

        public Builder withCommission(double commission) {
            this.commission = commission>=1d?commission/100d:commission;
            return this;
        }

        public ClientPriceFromCSVString build(){
            return new ClientPriceFromCSVString(this);
        }
    }

    /**
     * Convert a CSV string to an ClientPrice object
     * @param csvString CSV string
     * @return ClientPrice object
     */
    public ClientPriceDto getFromCSVString(String csvString){
        DateFormat format = new SimpleDateFormat(dateFormat);
        String[] csv = csvString.split(csvSeparator);
        ClientPriceDto clientPrice = new ClientPriceDto();

        clientPrice.setId(Integer.parseInt(csv[0].trim()));

        clientPrice.setCurrencies(csv[1].trim());

        BigDecimal bid = new BigDecimal(csv[2].trim());
        clientPrice.setBid(bid.subtract(bid.multiply(new BigDecimal(String.valueOf(commission)))));

        BigDecimal ask = new BigDecimal(csv[3].trim());
        clientPrice.setAsk(ask.add(ask.multiply(new BigDecimal(String.valueOf(commission)))));

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern(Config.DATE_FORMAT_MPF);
        LocalDateTime dateTime = LocalDateTime.parse(csv[4].trim(), formatter);
        ZoneId london = ZoneId.of("Europe/London");
        ZonedDateTime zdt = dateTime.atZone(london);
        clientPrice.setZonedDateTime(zdt);
        return clientPrice;
    }
}
