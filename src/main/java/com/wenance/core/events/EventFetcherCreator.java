package com.wenance.core.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.CurrencyExchangeResponseDTO;
import com.wenance.core.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
public class EventFetcherCreator {

    private final CurrencyRepository currencyRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public EventFetcherCreator(final CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void create() {
        final LocalDateTime timestamp = LocalDateTime.now();
        currencyRepository.save(timestamp, getData());
        log.info("Event created!");
    }

    private CurrencyExchangeResponseDTO getData(){
        log.info("Get Currency");
        String response =  webClientBuilder.build()
                .get()
                .uri("https://cex.io/api/last_price/BTC/USD")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        CurrencyExchangeResponseDTO currencyExchange =
                new CurrencyExchangeResponseDTO();
        ObjectMapper mapper = new ObjectMapper();
        try {
            currencyExchange = mapper.readValue(response, CurrencyExchangeResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error Mapeo");
        }
        return currencyExchange;
    }
}
