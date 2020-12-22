package com.wenance.core.service;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.repository.CurrencyRepository;
import com.wenance.core.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService{

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Optional<CurrencyExchange> getExchangeByTime(LocalDateTime timeStamp){
        log.info("Get Currency");
        return currencyRepository.getExchangeByTime(timeStamp);
    }

    @Override
    public Map<LocalDateTime, CurrencyExchange> getAllCurrencyExchanges() {
        return currencyRepository.getCurrencyExchangeTimeMap();
    }

    @Override
    public StaditicalExchange getStadisticalExchange(LocalDateTime time1, LocalDateTime time2) {

        DoubleSummaryStatistics stats =    currencyRepository
                .getAllCurrency()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().isAfter(time1.minusSeconds(1)) && e.getKey().isBefore(time2.plusSeconds(1)))
                .mapToDouble(x -> Double.parseDouble(x.getValue().getPrice()))
                .summaryStatistics();

        return StaditicalExchange.builder()
                .timeDesde(time1)
                .timeHasta(time2)
                .promedio(stats.getAverage())
                .diferenciaPorcentual(Utils
                        .calcularDiferencialPorcentual(stats.getAverage(), stats.getMax()))
                .build();
    }
}
