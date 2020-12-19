package com.wenance.core.models;

import lombok.Data;

@Data
public class CurrencyExchange {
    private String price;
    private String currencyFrom;
    private String currencyTo;
}
