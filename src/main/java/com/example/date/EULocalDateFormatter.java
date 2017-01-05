package com.example.date;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Janusz on 04.01.2017.
 */
public class EULocalDateFormatter implements Formatter<LocalDate> {
    public static final String EU_PATTERN="dd/MM/yyyy";
    public static final String NORMAL_PATTER="yyyy-MM-dd";

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return LocalDate.parse(s, DateTimeFormatter.ofPattern(EU_PATTERN));
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return DateTimeFormatter.ofPattern(getPattern(locale)).format(localDate);
    }

    public static String getPattern(Locale locale){
        return isEurope(locale)? NORMAL_PATTER : EU_PATTERN;
    }

    private static boolean isEurope(Locale locale){
        return Locale.GERMANY.getCountry().equals(locale.getCountry());
    }
}
