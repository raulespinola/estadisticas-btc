package com.wenance.core.utils;

import com.wenance.core.models.CurrencyExchange;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class Utils {

    public static Optional<LocalDateTime> convertStringToLocalDateTime(String date){
        Optional<LocalDateTime> dateTime = Optional.empty();
        try {
            dateTime = Optional.of(LocalDateTime.parse(date));
        } catch (DateTimeParseException e) {
            log.error("Error Parser: {}", e.getMessage());
        }
        return dateTime;
    }

    public static Optional<Double> calcularDiferencialPorcentual(double promedio, double maxValue){
        Optional<Double> result = Optional.empty();
        try{
           result = Optional.of((maxValue-promedio)*100/promedio);
        } catch (ArithmeticException e){
            log.error("Error Aritmetic,{}", e.getMessage());
        }
        return result;
    }


}
