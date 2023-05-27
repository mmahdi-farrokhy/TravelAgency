package model.submodel;

import commonStructures.CurrencyType;

import java.util.Objects;

public class Price {
    private final double amount;
    private final CurrencyType currency;

    public Price(double amount, CurrencyType currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.amount, amount) == 0 && currency == price.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
