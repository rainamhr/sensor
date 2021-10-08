package com.mobile.sensor;

import java.util.HashMap;

public class Utils {
    private static HashMap<String, String> map = new HashMap<>();

    static {
        map.put("Pitch Black", "#000000");
        map.put("Grey", "#979797");
        map.put("Normal", "#EAEAEA");
        map.put("Incredibly Bright", "#FFFCFC");
        map.put("This light will blind you", "#FFFFFF");

    }
    public static String convert(String ch) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < ch.length(); i++) {
            String c = String.valueOf(ch.charAt(i));
                buffer.append(c);
        }
        return buffer.toString();
    }
}