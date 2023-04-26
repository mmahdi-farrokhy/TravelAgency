package buisnessLayer;

import commonStructures.CurrencyType;

public class Price {
    private double amount;
    private CurrencyType currency;

    public Price(double amount, CurrencyType currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public CurrencyType getCurrency() {
        return currency;
    }
}
