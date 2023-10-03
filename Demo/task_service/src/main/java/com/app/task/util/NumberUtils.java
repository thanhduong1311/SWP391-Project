package com.app.task.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class NumberUtils {

    private static final String DOUBLE_REGEX_PATTERN = "(-?[0-9]+(?:[.][0-9]+)?)";

    public static Double extractDoubleFromString(String string) {
        try {
            Pattern pattern = Pattern.compile(DOUBLE_REGEX_PATTERN);
            Matcher matcher = pattern.matcher(string);
            String doubleString = "";
            if (matcher.find() && !((doubleString = matcher.group(0)).isEmpty())) {
                return Double.parseDouble(doubleString);
            }
            return null;
        } catch (Exception ex) {
            log.error("Parse error: {}", ex.getMessage());
        }
        return null;
    }

    public static BigDecimal extractMoneyFromString(String string) {
        try {
            Pattern pattern = Pattern.compile(DOUBLE_REGEX_PATTERN);
            Matcher matcher = pattern.matcher(string);
            String doubleString = "";
            if (matcher.find() && !((doubleString = matcher.group(0)).isEmpty())) {
                return new BigDecimal(doubleString);
            }
            return null;
        } catch (Exception ex) {
            log.error("Parse error: {}", ex.getMessage());
        }
        return null;
    }

    public static BigDecimal toBigDecimal(String value) {
        try {
            if (value == null || value.isEmpty()) {
                return null;
            }
            return new BigDecimal(value);
        } catch (Exception ex) {
            log.error("Parse error: {}", ex.getMessage());
            return null;
        }
    }
}
