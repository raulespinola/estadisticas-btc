package com.wenance.core.repository;

import com.wenance.core.models.CurrencyExchange;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Slf4j
@Repository
public class CurrencyExchangeRepository {

    private Map<LocalDateTime, CurrencyExchange> currencyExchangeTimeMap;
    public CurrencyExchangeRepository() {
        this.currencyExchangeTimeMap = new TreeMap<>();
    }

    public Map<LocalDateTime, CurrencyExchange> saveAll() {
        return currencyExchangeTimeMap;
    }

    public String save(LocalDateTime date, CurrencyExchange currencyExchange){
        currencyExchangeTimeMap.put(date, currencyExchange);
        return "Currency Exchange Guardado";
    }

    public Map<LocalDateTime, CurrencyExchange> getAllCurrency(){
        return currencyExchangeTimeMap;
    }

    public Optional<CurrencyExchange> getExchangeByTime(LocalDateTime localDateTime){
        log.info("Get Exchange");
        return Optional.of(currencyExchangeTimeMap.get(localDateTime));
    }
}
