package es.bermo.emilio.util;

public enum FXPair{
    EURUSD("EUR/USD"), GBPUSD("GBP/USD"), EURJPY("EUR/JPY");

    private String description;

    FXPair(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static FXPair switchCurrencies(String currencies) {
        switch (currencies) {
            case "EUR/USD":
                return FXPair.EURUSD;

            case "GBP/USD":
                return FXPair.GBPUSD;

            case "EUR/JPY":
                return FXPair.EURJPY;

            default:
                return null;
        }
    }
}
