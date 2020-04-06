package com.chilin.org.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

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

    public static byte[] convertStringToByteArray(String input){
        return input.getBytes(Charset.forName("UTF-8"));
    }

    public static String convertByteArrayToString(byte[] input){
        return new String(input, Charset.forName("UTF-8"));
    }

    public static String convertList(){
        List<String[]> dbContent = new ArrayList<>();
        String[] primero = {"30-03-2020","12:13","15:13"};
        String[] segundo = {"30-04-2020","12:13","15:13"};
        String[] tercero = {"30-05-2020","12:13","15:13"};
        dbContent.add(primero);
        dbContent.add(segundo);
        dbContent.add(tercero);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<dbContent.size();i++) {
            String[] content = dbContent.get(i);

            stringBuilder.append(content[0]);
            stringBuilder.append(',');
            stringBuilder.append(content[1]);
            stringBuilder.append(',');
            stringBuilder.append(content[2]);
            if (i != dbContent.size() - 1){
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        return stringBuilder.toString();
    }

}
