package com.wenance.core.service;

import com.wenance.core.models.CurrencyExchangeResponseDTO;
import com.wenance.core.models.StaditicalExchange;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public interface CurrencyExchangeService {
    CurrencyExchangeResponseDTO getExchangeByTime(LocalDateTime timeStamp);

    Map<LocalDateTime, CurrencyExchangeResponseDTO> getCurrencyMap();

    StaditicalExchange getStadisticalExchange(LocalDateTime time1, LocalDateTime time2);
}
