package com.bank.usecase.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Random;

public class Utils {

    static final int lowerNumberASCII = 48;
    static final int upperNumberASCII = 58;

    public static String randomNumeric(short count) {
        Random random = new Random();
        return random.ints(lowerNumberASCII, upperNumberASCII)
                .limit(count)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String zeros(int count) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < count) {
            sb.append('0');
        }
        return sb.toString();
    }

    public static LocalDate expirationDate(int expirationYears) {
        Calendar expirationCalendar = Calendar.getInstance();
        expirationCalendar.add(Calendar.YEAR, expirationYears);
        return expirationCalendar.getTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
