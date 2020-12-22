package com.wenance.core.service;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.repository.CurrencyExchangeRepository;
import com.wenance.core.utils.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


class CurrencyExchangeServiceImplTest {

    private CurrencyExchangeService currencyExchangeService;

    private CurrencyExchangeRepository currencyExchangeRepository;


    @BeforeEach
    void setUp() {
        currencyExchangeRepository = mock(CurrencyExchangeRepository.class);
        currencyExchangeService = new CurrencyExchangeServiceImpl(currencyExchangeRepository);
    }

    @Test
    void get_exchange_by_timestamp() {
        //Given
        Optional<LocalDateTime> fecha1=
                Utils.convertStringToLocalDateTime("2020-12-21T19:27:25.000");
        Optional<CurrencyExchange> currencyExchange =
                Optional.of(new CurrencyExchange("5000","USD","BTC"));
        given(currencyExchangeRepository.getExchangeByTime(fecha1.get())).willReturn(currencyExchange);

        //When
        Optional<CurrencyExchange> currencyExchangeReturn
                = currencyExchangeService.getExchangeByTime(fecha1.get());

        //Then
        assertThat(currencyExchangeReturn)
                .isPresent()
                .get().hasFieldOrPropertyWithValue("price", "5000");
    }

    @Test
    void getAllCurrencyExchanges() {
            //Given
            Optional<LocalDateTime> fecha1=
                    Utils.convertStringToLocalDateTime("2020-12-21T19:27:25.000");
            Optional<CurrencyExchange> currencyExchange =
                    Optional.of(new CurrencyExchange("5000","USD","BTC"));
            given(currencyExchangeRepository.getExchangeByTime(fecha1.get())).willReturn(currencyExchange);

            //When
            Optional<CurrencyExchange> currencyExchangeReturn
                    = currencyExchangeService.getExchangeByTime(fecha1.get());

            //Then
            assertThat(currencyExchangeReturn)
                    .isPresent()
                    .get().hasFieldOrPropertyWithValue("price", "5000");
    }

    @Test
    void getStadisticalExchange() {
            //Given
            Optional<LocalDateTime> fecha1=
                    Utils.convertStringToLocalDateTime("2020-12-21T19:27:25.000");
            Optional<CurrencyExchange> currencyExchange =
                    Optional.of(new CurrencyExchange("5000","USD","BTC"));
            given(currencyExchangeRepository.getExchangeByTime(fecha1.get())).willReturn(currencyExchange);

            //When
            Optional<CurrencyExchange> currencyExchangeReturn
                    = currencyExchangeService.getExchangeByTime(fecha1.get());

            //Then
            assertThat(currencyExchangeReturn)
                    .isPresent()
                    .get().hasFieldOrPropertyWithValue("price", "5000");
    }
}