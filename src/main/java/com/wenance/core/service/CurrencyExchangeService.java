package com.wenance.core.service;

import com.wenance.core.models.CurrencyExchangeResponseDTO;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

public interface CurrencyExchangeService {
    Mono<CurrencyExchangeResponseDTO> getCurrency();
}
