package com.wenance.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Slf4j
public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

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
