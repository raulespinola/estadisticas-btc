package com.wenance.core.service;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.StaditicalExchange;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface CurrencyExchangeService {
    Optional<CurrencyExchange> getExchangeByTime(LocalDateTime timeStamp);

    Map<LocalDateTime, CurrencyExchange> getAllCurrencyExchanges();

    Optional<StaditicalExchange> getStadisticalExchange(Optional<LocalDateTime> timeDesde, Optional<LocalDateTime> timeHasta);
}
