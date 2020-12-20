package com.wenance.core.controller;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.CurrencyExchangeResponseDTO;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.service.CurrencyExchangeService;
import com.wenance.core.utils.Utils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("v1/currency")
@Slf4j
@Api(value = "SaldosController", produces = "text/json", tags = {"Controlador Currency Exchange"})
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping(value="/getPriceByTime")
    public CurrencyExchangeResponseDTO getCurrency(String timeStampString) {
        LocalDateTime timestamp =  Utils.convertStringToLocalDateTime(timeStampString);
        return currencyExchangeService.getExchangeByTime(timestamp);
    }

    @GetMapping(value="/getAllExchangeCurrency")
    public Map<LocalDateTime, CurrencyExchangeResponseDTO> getAllCurrencyExchanges(){
        return currencyExchangeService.getCurrencyMap();
    }

    @GetMapping(value="/getExchangeStaditical")
    public StaditicalExchange getAllCurrencyExchanges(String time1, String time2){

        LocalDateTime timestamp1 =  Utils.convertStringToLocalDateTime(time1);
        LocalDateTime timestamp2 =  Utils.convertStringToLocalDateTime(time2);

        return currencyExchangeService.getStadisticalExchange(timestamp1, timestamp2);
    }
}
