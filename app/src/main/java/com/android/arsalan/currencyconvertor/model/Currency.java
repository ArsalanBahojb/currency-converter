package com.android.arsalan.currencyconvertor.model;

/**
 * Created by Arsalan
 * on 2017-05-28.
 */
public class Currency {
    private String symbol;
    private double value;

    public Currency(String symbol, double value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getValue() {
        return value;
    }

}
