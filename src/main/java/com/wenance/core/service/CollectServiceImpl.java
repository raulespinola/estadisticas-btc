package com.wenance.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenance.core.config.AppProperties;
import com.wenance.core.exceptions.ServiceException;
import com.wenance.core.exceptions.ServerCommunicationException;
import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.repository.CurrencyExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class CollectServiceImpl implements CollectService{

    private final CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private AppProperties appProperties;

    public CollectServiceImpl(final CurrencyExchangeRepository currencyExchangeRepository) {
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void createEventCollect() {
        final LocalDateTime timestamp = LocalDateTime.now();
        Optional<CurrencyExchange> currencyExchangeData = getExchangeData();
        if (currencyExchangeData.isPresent()) {
            currencyExchangeRepository.save(timestamp, currencyExchangeData.get());
        } else {
            throw new ServiceException("Data could not collect");
        }
        log.info("Collect Event Save");
    }


    private Optional<CurrencyExchange> getExchangeData(){
        log.info("Get Currency Exchange From Service");
        String response =  webClientBuilder.build()
                .get()
                .uri(appProperties.getExchangeUrl()+appProperties.getExchangeEndpoint())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        clientResponse -> clientResponse.bodyToMono(ServerCommunicationException.class)
                                .flatMap(serverCommunicationException -> Mono.error(serverCommunicationException)))
                .onStatus(HttpStatus::isError,
                        clientResponse -> clientResponse.bodyToMono(ServiceException.class)
                                .flatMap(serviceException -> Mono.error(serviceException)))
                .bodyToMono(String.class)
                .block();

        Optional<CurrencyExchange> currencyExchange = Optional.empty();
        ObjectMapper mapper = new ObjectMapper();
        try {
            currencyExchange = Optional.of(mapper.readValue(response, CurrencyExchange.class));
        } catch (JsonProcessingException e) {
            log.error("Error Mapeo {}", e.getMessage());
        }
        return currencyExchange;
    }
}
