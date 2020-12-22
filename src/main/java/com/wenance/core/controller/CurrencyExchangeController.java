package com.wenance.core.controller;

import com.wenance.core.exceptions.ExceptionResponse;
import com.wenance.core.models.CurrencyExchange;
import com.wenance.core.models.StaditicalExchange;
import com.wenance.core.service.CurrencyExchangeService;
import com.wenance.core.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "CurrencyExchange Controller", produces = "text/json", tags = {"Controlador Currency Exchange"})
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping(value="/getExchangeByTime")
    @ApiOperation(value = "Obtiene Exchange BTC/USD por TimeStamp en String", tags = { "CurrencyExchange Controller" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exchange Encontrada", response = CurrencyExchange.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error en el servidor", response = ExceptionResponse.class)})
    public ResponseEntity<CurrencyExchange> getCurrencyExchangeByTimeStamp(@PathVariable String timeStampString) {
        LocalDateTime timestamp =  Utils.convertStringToLocalDateTime(timeStampString);
        return currencyExchangeService.getExchangeByTime(timestamp)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value="/getAllExchangeCurrency")
    @ApiOperation(value = "Obtiene todos Exchange BTC/USD recolectados", tags = { "CurrencyExchange Controller" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista Encontrada", response = CurrencyExchange.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error en el servidor", response = ExceptionResponse.class)})
    public ResponseEntity<Map<LocalDateTime, CurrencyExchange>> getAllCurrencyExchanges(){
        return new ResponseEntity(currencyExchangeService.getAllCurrencyExchanges(), HttpStatus.OK);
    }

    @GetMapping(value="/getExchangeStaditical")
    @ApiOperation(value = "Obtiene datos Estadisticos, Promedio y Porcentaje Dieferencia",
            tags = { "CurrencyExchange Controller" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista Encontrada", response = CurrencyExchange.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error en el servidor", response = ExceptionResponse.class)})
    public ResponseEntity<StaditicalExchange> getExchangeStadistical(@PathVariable String timeFrom,
                                                      @PathVariable String timeTo){
        LocalDateTime timestamp1 =  Utils.convertStringToLocalDateTime(timeFrom);
        LocalDateTime timestamp2 =  Utils.convertStringToLocalDateTime(timeTo);
        return new ResponseEntity(currencyExchangeService
                .getStadisticalExchange(timestamp1, timestamp2), HttpStatus.OK);
    }
}
