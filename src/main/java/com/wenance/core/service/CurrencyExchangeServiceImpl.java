package com.wenance.core.service;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.repository.CurrencyExchangeRepository;
import com.wenance.core.utils.Utils;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService{

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public CurrencyExchangeServiceImpl(CurrencyExchangeRepository currencyExchangeRepository) {
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    public Optional<CurrencyExchange> getExchangeByTime(LocalDateTime timeStamp){
        log.info("Get Currency");
        return currencyExchangeRepository.getExchangeByTime(timeStamp);
    }

    @Override
    public Map<LocalDateTime, CurrencyExchange> getAllCurrencyExchanges() {
        return currencyExchangeRepository.getCurrencyExchangeTimeMap();
    }

    @Override
    public Optional<StaditicalExchange> getStadisticalExchange(Optional<LocalDateTime> time1, Optional<LocalDateTime> time2) {

        Optional<StaditicalExchange> result = Optional.empty();
        Optional<Double> calc;
        DoubleSummaryStatistics stats;
        if (time1.isPresent() && time2.isPresent()) {
            stats = currencyExchangeRepository
                    .getAllCurrency()
                    .entrySet()
                    .stream()
                    .filter(e -> e.getKey().isAfter(time1.get().minusSeconds(1))
                            && e.getKey().isBefore(time2.get().plusSeconds(1)))
                    .mapToDouble(x -> Double.parseDouble(x.getValue().getPrice()))
                    .summaryStatistics();
            calc = Utils.calcularDiferencialPorcentual(stats.getAverage(), stats.getMax());

            if (calc.isPresent())
                result = Optional
                        .of(StaditicalExchange.builder()
                                .timeDesde(time1.get())
                                .timeHasta(time2.get())
                                .promedio(stats.getAverage())
                                .diferenciaPorcentual(calc.get())
                                .build());
            return result;
        }
        return result;
    }
}
