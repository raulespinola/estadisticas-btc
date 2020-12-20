package com.wenance.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenance.core.models.CurrencyExchangeResponseDTO;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService{

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public CurrencyExchangeResponseDTO getExchangeByTime(LocalDateTime timeStamp){
        log.info("Get Currency");
        return currencyRepository.getExchangeByTime(timeStamp);
    }

    @Override
    public Map<LocalDateTime, CurrencyExchangeResponseDTO> getCurrencyMap() {
        return currencyRepository.getCurrencyExchangeTimeMap();
    }

    @Override
    public StaditicalExchange getStadisticalExchange(LocalDateTime time1, LocalDateTime time2) {

        Map<LocalDateTime, CurrencyExchangeResponseDTO> currencyExchangeTimeMap=
                currencyRepository.getAllCurrency();

        List<CurrencyExchangeResponseDTO> exchangeResponseDTOList =  currencyExchangeTimeMap
                .entrySet()
                .stream()
                .filter(e -> e.getKey().isAfter(time1.minusSeconds(1)) && e.getKey().isBefore(time2.plusSeconds(1)))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        DoubleSummaryStatistics stats = exchangeResponseDTOList
                .stream()
                .mapToDouble(x -> Double.parseDouble(x.getPrice()))
                .summaryStatistics();

        log.info("List de CurrencyExchanges, {}", exchangeResponseDTOList);
        log.info("Estaditicas, {}", stats);

        return new StaditicalExchange();
    }


}
