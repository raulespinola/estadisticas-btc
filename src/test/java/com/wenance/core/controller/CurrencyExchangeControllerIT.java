package com.wenance.core.controller;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.repository.CurrencyExchangeRepository;
import com.wenance.core.service.CurrencyExchangeService;
import com.wenance.core.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
 class CurrencyExchangeControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private   CurrencyExchangeRepository currencyExchangeRepository;
    private Map<LocalDateTime, CurrencyExchange> currencyExchangeMap;

    @BeforeEach
    void setUp() {
        currencyExchangeMap = new TreeMap<>();
        Optional<LocalDateTime> fecha1 = Utils.convertStringToLocalDateTime("2020-12-21T19:27:25.000");
        if (fecha1.isPresent()) {
            currencyExchangeMap.put(fecha1.get(),
                    new CurrencyExchange("5000","USD","BTC"));
            currencyExchangeRepository.save(fecha1.get(),
                    new CurrencyExchange("5000","USD","BTC"));
        }
        Optional<LocalDateTime> fecha2 = Utils.convertStringToLocalDateTime("2020-12-21T19:27:30.000");
        if (fecha2.isPresent()) {
            currencyExchangeMap.put(fecha2.get(),
                    new CurrencyExchange("4000","USD","BTC"));
            currencyExchangeRepository.save(fecha2.get(),
                    new CurrencyExchange("4000","USD","BTC"));
        }
        Optional<LocalDateTime> fecha3 = Utils.convertStringToLocalDateTime("2020-12-21T19:27:35.000");
        if (fecha3.isPresent()) {
            currencyExchangeMap.put(fecha3.get(),
                    new CurrencyExchange("6000","USD","BTC"));
            currencyExchangeRepository.save(fecha3.get(),
                    new CurrencyExchange("6000","USD","BTC"));
        }
    }

    @Test
    void get_all_currency_exchanges_it() throws Exception {
        this.mockMvc.perform(get("/v1/currencyExchange/getAllExchangeCurrency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(currencyExchangeMap.size())));
    }

    @Test
    void get_currency_exchange_by_time_it() throws Exception {
        String timeStampString = "2020-12-21T19:27:25.000";
        Optional<LocalDateTime> timeStampID = Utils.convertStringToLocalDateTime(timeStampString);
        CurrencyExchange currencyExchange = new CurrencyExchange("5000",
                "USD", "BTC");
        this.mockMvc.perform(get("/v1/currencyExchange/getExchangeByTime/timeStamp/{timeStamp}/",
                timeStampString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lprice", is(currencyExchange.getPrice())));
    }

    @Test
    void get_stadistical_data_average_and_porcentual_difference_it() throws Exception {
        Optional<LocalDateTime> timeDesde= Utils.convertStringToLocalDateTime("2020-12-21T19:27:25.000");
        Optional<LocalDateTime> timeHasta=Utils.convertStringToLocalDateTime("2020-12-21T19:27:35.000");
        StaditicalExchange staditicalExchangeMock = StaditicalExchange.builder()
                .diferenciaPorcentual(20)
                .promedio(5000)
                .timeDesde(timeDesde.get())
                .timeHasta(timeHasta.get())
                .build();

        this.mockMvc.perform(get("/v1/currencyExchange/getExchangeStaditical/timeFrom/{timeDesde}/timeTo/{timeHasta}/",
                timeDesde.get(), timeHasta.get()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promedio", is(staditicalExchangeMock.getPromedio())))
                .andExpect(jsonPath("$.diferenciaPorcentual", is(staditicalExchangeMock.getDiferenciaPorcentual())));
    }
}