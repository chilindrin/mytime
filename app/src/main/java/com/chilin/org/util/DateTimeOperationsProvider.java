package com.chilin.org.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

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

    public static boolean isBeginnPausePailas(String currentDate, String comingTime, String leavingTime, String beginnPause, String endePause) {
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        Date leavingTimeComplete = createDateForTime(currentDate, leavingTime);
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date endePauseComplete = createDateForTime(currentDate, endePause);
        return beginnPauseComlete.before(comingTimeComplete)
                || beginnPauseComlete.equals(comingTimeComplete)
                || beginnPauseComlete.after(endePauseComplete)
                || beginnPauseComlete.equals(endePauseComplete)
                || beginnPauseComlete.after(leavingTimeComplete);
    }

    public static boolean isBeginnPauseNotAfterComingTimeNorBeforeEndePause(String currentDate, String comingTime, String beginnPause, String endePause) {
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date endePauseComplete = createDateForTime(currentDate,endePause);
        return beginnPauseComlete.before(comingTimeComplete) ||
                beginnPauseComlete.equals(comingTimeComplete) ||
                beginnPauseComlete.equals(endePauseComplete) ||
                beginnPauseComlete.after(endePauseComplete);
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

    public static void validateBeginnPause(Day registeredDay, String beginnPause) {
        String currentDate = registeredDay.getDayRegistered();
        String leavingTime = registeredDay.getLeavingTime();
        String comingTime = registeredDay.getComingTime();
        String endePause = registeredDay.getEndePause();
        if (StringUtils.isNotBlank(comingTime)
                && StringUtils.isNotBlank(leavingTime)
                && StringUtils.isNotBlank(endePause)) {
            if (isBeginnPausePailas(currentDate, comingTime, leavingTime, beginnPause, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (StringUtils.isNotBlank(comingTime)
                && StringUtils.isBlank(leavingTime)
                && StringUtils.isNotBlank(endePause)) {
            if (isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate, comingTime, beginnPause, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (StringUtils.isBlank(comingTime)
                && StringUtils.isNotBlank(endePause)
        && StringUtils.isNotBlank(leavingTime)) {
            if (isBeginnPauseNotBeforeEndePauseAndNotBeforeLeavingTime(currentDate, beginnPause, endePause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }
    }

    private static boolean isBeginnPauseNotBeforeEndePauseAndNotBeforeLeavingTime(String currentDate, String beginnPause, String endePause, String leavingTime) {
        return true;
    }

    public static void validateComingTime(Day registeredDay, String comingTime) {

    }
}
