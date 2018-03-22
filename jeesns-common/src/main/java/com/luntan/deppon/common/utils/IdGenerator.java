//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.luntan.deppon.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdGenerator {
    private static Long n10000000000000=10000000000000L;
    private static Long n4=4L;
    private static Long n10=10L;
    private static Long n48=48L;
    private static Long n57=57L;
    public IdGenerator() {
    }

    public static long generateId(long sequence, String no) {
        long prefix = n10000000000000.longValue() + sequence;
        return Long.valueOf(prefix + tail4(extractNumberFromString(no))).longValue();
    }

    public static String numberToString(long data, int digit) {
        Double prefix = Double.valueOf(Math.pow((double)n10.longValue(), (double)digit));
        Long result = Long.valueOf(prefix.longValue() + data);
        String str = result.toString();
        return str.substring(1, str.length());
    }

    public static String extractNumberFromString(String str) {
        StringBuffer result = new StringBuffer();
        str = str.trim();

        for(int i = 0; i < str.length(); ++i) {
            if((long)str.charAt(i) >= n48.longValue() && (long)str.charAt(i) <= n57.longValue()) {
                result = result.append(str.charAt(i));
            }
        }

        return result.toString();
    }

    public static String tail4(String str) {
        str = "0000" + str;
        return str.substring(str.length() - (int)n4.longValue(), str.length());
    }

    public static void main(String[] args) {
        System.out.println(numberToString(2L, 2));
        System.out.println(extractNumberFromString("sd234sd234kf1d"));
        System.out.println(extractNumberFromString("sd234kfd"));
        System.out.println(extractNumberFromString(""));
        System.out.println(tail4("123"));
        System.out.println(tail4(extractNumberFromString("")));
        System.out.println(tail4(extractNumberFromString("sd234kfd")));
        System.out.println(tail4(extractNumberFromString("sd234sd234kf1d")));
    }

    public static String filterUnNumber(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("0").trim();
    }
}
