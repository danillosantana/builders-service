package br.builders.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@UtilityClass
public class DateUtils {

    public static final String FORMATO_DATA_YYYY_MM_DD = "yyyy-MM-dd";


    public static LocalDate parserLocalDate(String date, String pattern) {
        try {
            DateTimeFormatter parser = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(date, parser);

        } catch (DateTimeParseException e) {
            log.error("Falha ao converter data detalhes: {} ", e);
        }

        return null;
    }

}
