package com.wenance.core.models;

import lombok.Data;

import java.util.Date;

@Data
public class CurrencyExchange {
    private String price;
    private String currencyFrom;
    private String currencyTo;
    private Date hourDate;
}
