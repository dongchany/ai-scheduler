package com.github.dongchan.scheduler;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 2:26 PM
 */
public class StringUtils {
    public static String truncate(String s, int length) {
        if (s == null) {
            return null;
        }

        if (s.length() > length) {
            return s.substring(0, length);
        } else {
            return s;
        }
    }
}
