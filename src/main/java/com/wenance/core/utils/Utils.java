package com.wenance.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class Utils {

    public static LocalDateTime convertStringToLocalDateTime(String date){
        //TODO: Check other return and exceptions
        LocalDateTime dateTime = null;
        dateTime = LocalDateTime.parse(date);
        return dateTime;
    }
}
