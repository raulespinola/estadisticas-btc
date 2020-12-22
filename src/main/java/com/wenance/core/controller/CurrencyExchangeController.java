package com.wenance.core.controller;

import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.service.CurrencyExchangeService;
import com.wenance.core.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("v1/currencyExchange")
@Slf4j
@Api(value = "CurrencyExchange Controller", produces = "text/json")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping(value="/getExchangeByTime/timeStamp/{timeStamp}")
    @ApiOperation(value = "Obtiene Exchange BTC/USD por TimeStamp en String")
    public ResponseEntity<CurrencyExchange> getCurrencyExchangeByTimeStamp(@PathVariable String timeStamp) {
        Optional<LocalDateTime> timestampDate =  Utils.convertStringToLocalDateTime(timeStamp);
        if(timestampDate.isPresent()){
        return currencyExchangeService.getExchangeByTime(timestampDate.get())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());}
        else return ResponseEntity.notFound().build();
    }

    @GetMapping(value="/getAllExchangeCurrency")
    @ApiOperation(value = "Obtiene todos Exchange BTC/USD recolectados")
    public ResponseEntity<Map<LocalDateTime, CurrencyExchange>> getAllCurrencyExchanges(){
        return new ResponseEntity<>(currencyExchangeService.getAllCurrencyExchanges(), HttpStatus.OK);
    }

    @GetMapping(value="/getExchangeStaditical/timeFrom/{timeFrom}/timeTo/{timeTo}")
    @ApiOperation(value = "Obtiene datos Estadisticos, Promedio y Porcentaje Dieferencia")
    public ResponseEntity<StaditicalExchange> getExchangeStadistical(@PathVariable String timeFrom,
                                                      @PathVariable String timeTo){
        Optional<LocalDateTime> timestamp1 =  Utils.convertStringToLocalDateTime(timeFrom);
        Optional<LocalDateTime> timestamp2 =  Utils.convertStringToLocalDateTime(timeTo);
        return currencyExchangeService
                .getStadisticalExchange(timestamp1, timestamp2)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
