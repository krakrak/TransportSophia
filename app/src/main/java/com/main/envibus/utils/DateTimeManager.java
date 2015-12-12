package com.main.envibus.utils;

import java.util.Calendar;

/**
 * Created by KraKk on 20/09/2015.
 */
public class DateTimeManager {

    public static Calendar setCurrentTimeAndDateOnView () {

        final Calendar c = Calendar.getInstance();

        return c;
    }

    public static String pad(int c) {

        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }

}
