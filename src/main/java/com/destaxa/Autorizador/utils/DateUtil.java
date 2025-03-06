package com.destaxa.Autorizador.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DateUtil {

    public static String pegarData(Date date) {
        if(Objects.isNull(date))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(date);
        return format;
    }

    public static String pegarDataFormato(Date data, String formato) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            String format = formatter.format(data);
            return format;
        }catch (Exception e) {
            log.error("[DateUtil] Erro ao transformar data ->",e);
            return "";
        }
    }

    public static String pegarDataFormato(LocalDateTime data, String formato) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            String format = formatter.format(data);
            return format;
        }catch (Exception e) {
            log.error("[DateUtil] Erro ao transformar data ->",e);
            return "";
        }
    }

    public static String pegarhora(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        String format = formatter.format(date);
        return format;
    }

    public static Date getDataAtual() {
        var dataAtual = LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo"));
        var instant = Instant.from(dataAtual);
        return Date.from(instant);
    }
}
