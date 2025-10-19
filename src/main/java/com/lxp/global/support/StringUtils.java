package com.lxp.global.support;

public class StringUtils {

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

}
