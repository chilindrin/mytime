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

    public static void validateBeginnPause(Day registeredDay, String beginnPause) {
        String currentDate = registeredDay.getDayRegistered();
        String comingTime = registeredDay.getComingTime();
        String endePause = registeredDay.getEndePause();
        String leavingTime = registeredDay.getLeavingTime();
        if (BinaryConditions.condition001(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginPauseNotBeforeLeavingTime(currentDate, beginnPause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition010(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginPauseNotBeforeEndePause(currentDate, beginnPause, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition011(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotBeforeEndePauseAndNotBeforeLeavingTime(currentDate, beginnPause, endePause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition100(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotAfterComingTime(currentDate, beginnPause, comingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition101(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotBetweenComingAndLeavingTime(currentDate, beginnPause, comingTime,leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition110(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate, beginnPause, comingTime, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition111(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPausePailas(currentDate, beginnPause,comingTime,endePause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }
    }

    public static void validateComingTime(Day registeredDay, String comingTime) {
        String currentDate = registeredDay.getDayRegistered();
        String beginnPause = registeredDay.getBeginnPause();
        String endePause = registeredDay.getEndePause();
        String leavingTime = registeredDay.getLeavingTime();
        if (BinaryConditions.condition001(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isBeginPauseNotBeforeLeavingTime(currentDate, comingTime, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition010(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isBeginPauseNotBeforeEndePause(currentDate, comingTime, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition011(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotBeforeEndePauseAndNotBeforeLeavingTime(currentDate, comingTime, endePause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition100(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotAfterComingTime(currentDate, comingTime, comingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition101(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotBetweenComingAndLeavingTime(currentDate, comingTime, comingTime,leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition110(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate, comingTime, comingTime, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition111(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPausePailas(currentDate, comingTime,comingTime,endePause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }

    }

}
