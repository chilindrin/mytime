package com.chilin.org.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class DateProvider {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFriendlyFormatCurrentDate(LocalDateTime currentDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(currentDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime getCurrentDate(){
        return LocalDateTime.now();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isWeekend(LocalDateTime currentDate){
        DayOfWeek dayOfWeek = DayOfWeek.of(currentDate.get(ChronoField.DAY_OF_WEEK));
        switch (dayOfWeek) {
            case SATURDAY:
            case SUNDAY:
                return true;
            default:
                return false;
        }
    }

}
