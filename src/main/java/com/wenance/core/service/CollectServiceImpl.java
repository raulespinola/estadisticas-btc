package com.wenance.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenance.core.models.CurrencyExchangeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
public class CollectServiceImpl {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public CurrencyExchangeResponseDTO getData(){
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
