package es.bermo.emilio.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


public class ClientPriceDto {
    private Integer id;
    private String currencies;
    private BigDecimal bid;
    private BigDecimal ask;
    private ZonedDateTime zonedDateTime;

    public ClientPriceDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    @Override
    public String toString() {
        return "ClientPriceDto{" +
                "id=" + getId() +
                ", currencies='" + getCurrencies() + '\'' +
                ", bid=" + getBid() +
                ", ask=" + getAsk() +
                ", zonedDateTime=" + getZonedDateTime() +
                '}';
    }
}
