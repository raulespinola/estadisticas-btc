package com.wenance.core.controller;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.service.CurrencyExchangeService;
import com.wenance.core.utils.Utils;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CurrencyExchangeController.class)
class CurrencyExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    private Map<LocalDateTime,CurrencyExchange> currencyExchangeMap;

    @BeforeEach
    void setUp() {
        currencyExchangeMap = new TreeMap<>();
        Optional<LocalDateTime> fecha1=Utils.convertStringToLocalDateTime("2020-12-21T19:27:25.000");
        if (fecha1.isPresent()){
        currencyExchangeMap.put(fecha1.get(),
                new CurrencyExchange("5000","USD","BTC"));
        }
        Optional<LocalDateTime> fecha2=Utils.convertStringToLocalDateTime("2020-12-21T19:27:30.000");
        if (fecha2.isPresent()){
        currencyExchangeMap.put(fecha2.get(),
                new CurrencyExchange("4500","USD","BTC"));
        }
        Optional<LocalDateTime> fecha3=Utils.convertStringToLocalDateTime("2020-12-21T19:27:35.000");
        if (fecha3.isPresent()){
        currencyExchangeMap.put(fecha3.get(),
                new CurrencyExchange("6000","USD","BTC"));
        }
    }

    @Test
    void get_all_currency_exchanges_test() throws Exception {
        given(currencyExchangeService.getAllCurrencyExchanges()).willReturn(this.currencyExchangeMap);

        this.mockMvc.perform(get("/v1/currencyExchange/getAllExchangeCurrency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(currencyExchangeMap.size())));
    }

    @Test
    void get_currency_exchange_by_time_test() throws Exception {
        String timeStampString = "2020-12-21T19:27:25.000";
        Optional<LocalDateTime> timeStampID= Utils.convertStringToLocalDateTime(timeStampString);
        CurrencyExchange currencyExchange = new CurrencyExchange("6000",
                "USD","BTC");
        given(currencyExchangeService.getExchangeByTime(timeStampID.get()))
                .willReturn(Optional.of(currencyExchange));

        this.mockMvc.perform(get("/v1/currencyExchange/getExchangeByTime/timeStamp/{timeStamp}/", timeStampString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lprice", is(currencyExchange.getPrice())));
    }

    @Test
    void get_stadistical_data_average_and_porcentual_difference_test() throws Exception {

        Optional<LocalDateTime> timeDesde= Utils.convertStringToLocalDateTime("2020-12-21T21:21:13.893");
        Optional<LocalDateTime> timeHasta=Utils.convertStringToLocalDateTime("2020-12-21T21:21:53.885");
        StaditicalExchange staditicalExchangeMock = StaditicalExchange.builder()
                .diferenciaPorcentual(0)
                .promedio(100)
                .timeDesde(timeDesde.get())
                .timeHasta(timeHasta.get())
                .build();
        given(currencyExchangeService.getStadisticalExchange(timeDesde,timeHasta))
                .willReturn(Optional.of(staditicalExchangeMock));

        this.mockMvc.perform(get("/v1/currencyExchange/getExchangeStaditical/timeFrom/{timeDesde}/timeTo/{timeHasta}/",
                timeDesde.get(), timeHasta.get()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promedio", is(staditicalExchangeMock.getPromedio())));

    }
}