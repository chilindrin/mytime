package com.chilin.org.util;

import org.apache.commons.lang3.StringUtils;

public class BinaryConditions {

    public static boolean condition001(String firstLimit, String middle,String lastLimit) {
        return StringUtils.isBlank(firstLimit)
                && StringUtils.isBlank(middle)
                && StringUtils.isNotBlank(lastLimit);
    }

    public static boolean condition010(String firstLimit, String middle,String lastLimit) {
        return StringUtils.isBlank(firstLimit)
                && StringUtils.isNotBlank(middle)
                && StringUtils.isBlank(lastLimit);
    }

    public static boolean condition011(String firstLimit, String middle,String lastLimit) {
        return StringUtils.isBlank(firstLimit)
                && StringUtils.isNotBlank(middle)
                && StringUtils.isNotBlank(lastLimit);
    }

    public static boolean condition100(String firstLimit, String middle,String lastLimit) {
        return StringUtils.isNotBlank(firstLimit)
                && StringUtils.isBlank(middle)
                && StringUtils.isBlank(lastLimit);
    }

    public static boolean condition101(String firstLimit, String middle,String lastLimit) {
        return StringUtils.isNotBlank(firstLimit)
                && StringUtils.isBlank(middle)
                && StringUtils.isNotBlank(lastLimit);
    }

    public static boolean condition110(String firstLimit, String middle,String lastLimit) {
        return StringUtils.isNotBlank(firstLimit)
                && StringUtils.isNotBlank(middle)
                && StringUtils.isBlank(lastLimit);
    }

    public static boolean condition111(String firstLimit, String middle,String lastLimit) {
        return StringUtils.isNotBlank(firstLimit)
                && StringUtils.isNotBlank(middle)
                && StringUtils.isNotBlank(lastLimit);
    }
}
