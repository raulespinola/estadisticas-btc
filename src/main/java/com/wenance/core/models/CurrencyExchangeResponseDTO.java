package com.wenance.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CurrencyExchangeResponseDTO {
    @JsonProperty("lprice")
    private String price;
    @JsonProperty("curr1")
    private String currencyFrom;
    @JsonProperty("curr2")
    private String currencyTo;

    public String getPrice() {
        return price;
    }

    public CurrencyExchangeResponseDTO setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public CurrencyExchangeResponseDTO setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
        return this;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public CurrencyExchangeResponseDTO setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
        return this;
    }
}
