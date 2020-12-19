package com.wenance.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyExchangeResponseDTO {
    @JsonProperty("lprice")
    private String price;
    @JsonProperty("curr1")
    private String currencyFrom;
    @JsonProperty("curr2")
    private String currencyTo;
}
