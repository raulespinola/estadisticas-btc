package com.wenance.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CollectServiceImpl {

    private final CurrencyRepository currencyRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public CollectServiceImpl(final CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void createEventCollect() {
        final LocalDateTime timestamp = LocalDateTime.now();
        currencyRepository.save(timestamp, getExchangeData());
        log.info("Event created!");
    }

    private CurrencyExchange getExchangeData(){
        log.info("Get Currency");
        String response =  webClientBuilder.build()
                .get()
                .uri("https://cex.io/api/last_price/BTC/USD")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        CurrencyExchange currencyExchange =
                new CurrencyExchange();
        ObjectMapper mapper = new ObjectMapper();
        try {
            currencyExchange = mapper.readValue(response, CurrencyExchange.class);
        } catch (JsonProcessingException e) {
            log.error("Error Mapeo");
        }
        return currencyExchange;
    }

}
