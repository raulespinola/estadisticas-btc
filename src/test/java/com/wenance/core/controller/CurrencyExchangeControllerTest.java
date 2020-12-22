package com.wenance.core.controller;

import com.wenance.core.models.CurrencyExchange;
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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
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
        currencyExchangeMap.put(Utils.convertStringToLocalDateTime("2020-12-21T19:27:25.000"),
                new CurrencyExchange("5000","USD","BTC"));
        currencyExchangeMap.put(Utils.convertStringToLocalDateTime("2020-12-21T19:27:30.000"),
                new CurrencyExchange("4500","USD","BTC"));
        currencyExchangeMap.put(Utils.convertStringToLocalDateTime("2020-12-21T19:27:35.000"),
                new CurrencyExchange("6000","USD","BTC"));

    }

    @Test
    void get_all_currency_exchanges() throws Exception {
        given(currencyExchangeService.getAllCurrencyExchanges()).willReturn(this.currencyExchangeMap);

        this.mockMvc.perform(get("/v1/currencyExchange/getAllExchangeCurrency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(currencyExchangeMap.size())));
    }

    @Test
    void get_currency_exchange_by_time() throws Exception {
        String timeStampString = "2020-12-21T19:27:25.000";
        LocalDateTime timeStampID= Utils.convertStringToLocalDateTime(timeStampString);
        CurrencyExchange currencyExchange = new CurrencyExchange("6000",
                "USD","BTC");
        given(currencyExchangeService.getExchangeByTime(timeStampID))
                .willReturn(Optional.of(currencyExchange));

        this.mockMvc.perform(get("/v1/currencyExchange/getExchangeByTime?timeStampString={timeStampString}", timeStampString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(currencyExchange.getPrice())));
    }

    @Test
    void testGetAllCurrencyExchanges() {
    }
}