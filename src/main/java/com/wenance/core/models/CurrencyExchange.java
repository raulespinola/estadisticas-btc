package com.wenance.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchange {
    @JsonProperty("lprice")
    private String price;
    @JsonProperty("curr1")
    private String currencyFrom;
    @JsonProperty("curr2")
    private String currencyTo;

    public String getPrice() {
        return price;
    }

    public CurrencyExchange setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public CurrencyExchange setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
        return this;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public CurrencyExchange setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
        return this;
    }
}
