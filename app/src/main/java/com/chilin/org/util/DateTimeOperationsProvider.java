package com.chilin.org.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

public class DateTimeOperationsProvider {

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String createPause(String currentDay, String beginnPause, String endePause){
        if (StringUtils.isBlank(currentDay) || StringUtils.isBlank(beginnPause) || StringUtils.isBlank(endePause)){
            return "<->";
        }
        long pauseInMinutes = getPauseInMinutes(currentDay, beginnPause, endePause);

        if (pauseInMinutes <= 8){
            return "0";
        } else if (pauseInMinutes > 8 && pauseInMinutes <= 20){
            return "15";
        } else if (pauseInMinutes > 20 && pauseInMinutes <= 38){
            return "30";
        } else if (pauseInMinutes > 38 && pauseInMinutes <= 50){
            return "45";
        } else if (pauseInMinutes > 50){
            return "60";
        }
        throw new IllegalStateException("Du warst zu faul, deine Pause kontte nicht geparst werden");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static long getPauseInMinutes(String currentDay, String beginnPause, String endePause) {
        String pauseStartWithCurrentDay = currentDay + " " + beginnPause;
        String pauseEndeWithCurrentDay = currentDay + " " + endePause;

        DateTimeFormatter currentDayWithTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime pauseStart = LocalDateTime.parse(pauseStartWithCurrentDay,currentDayWithTimeFormatter);
        LocalDateTime pauseEnde = LocalDateTime.parse(pauseEndeWithCurrentDay,currentDayWithTimeFormatter);
        Duration pauseDuration = Duration.between(pauseStart,pauseEnde);
        long pauseInSeconds = pauseDuration.getSeconds();
        return pauseInSeconds / 60;
    }

    public static boolean isBeginnPauseBetweenComingAndLeavingTime(String currentDate, String comingTime, String leavingTime, String beginnPause) {
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        Date leavingTimeComplete = createDateForTime(currentDate, leavingTime);
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        return beginnPauseComlete.after(comingTimeComplete) && beginnPauseComlete.before(leavingTimeComplete);
    }

    public static boolean isBeginnPauseBeforeComingTime(String currentDate, String comingTime, String beginnPause) {
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        return beginnPauseComlete.before(comingTimeComplete);
    }

    public static boolean isBeginnPauseAfterLeavingTime(String currentDate, String leavingTime, String beginnPause) {
        Date leavingTimeComplete = createDateForTime(currentDate, leavingTime);
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        return beginnPauseComlete.after(leavingTimeComplete) || beginnPauseComlete.equals(leavingTimeComplete);
    }

    private static Date createDateForTime(String currentDate,String time){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timeString = currentDate + " " + time;
        Date dateForTime = null;
        try {
            dateForTime = formatter.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateForTime;
    }

}
