package com.wenance.core.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.CurrencyExchangeResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Slf4j
@Repository
public class CurrencyRepository {

    private Map<LocalDateTime, CurrencyExchangeResponseDTO> currencyExchangeTimeMap;


    public CurrencyRepository() {
        this.currencyExchangeTimeMap = new TreeMap<>();
    }

    public String save(LocalDateTime date, CurrencyExchangeResponseDTO currencyExchange){
        currencyExchangeTimeMap.put(date, currencyExchange);
        return "Currency Exchange Guardado";
    }

    public Map<LocalDateTime, CurrencyExchangeResponseDTO> getAllCurrency(){
        return currencyExchangeTimeMap;
    }

    public CurrencyExchangeResponseDTO getExchangeByTime(LocalDateTime localDateTime){
        log.info("Get Exchange");
        CurrencyExchangeResponseDTO currencyExchangeResponseDTO =
                currencyExchangeTimeMap.get(localDateTime);
        return currencyExchangeResponseDTO;
    }
}
