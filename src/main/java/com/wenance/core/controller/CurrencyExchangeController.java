package com.wenance.core.controller;

import com.wenance.core.exceptions.ExceptionResponse;
import com.wenance.core.models.CurrencyExchangeResponseDTO;
import com.wenance.core.service.CurrencyExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/currency")
@Slf4j
@Api(value = "SaldosController", produces = "application/json", tags = {"Controlador Currency Exchange"})
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @ApiOperation(value = "Obtener el precio del Bitcoin en cierto Timestamp.", tags = {"Bticoin Price"})
    @GetMapping("/getPriceByTime")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BTC Price Found", response = String.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error", response = ExceptionResponse.class)})
    public Mono<String> getCurrencyAtTimeStamp(){
        String response = currencyExchangeService.getCurrency();
        return response;
    }
}
