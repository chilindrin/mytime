package com.chilin.org.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

import org.apache.commons.lang3.StringUtils;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

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

    public static void validateComingTime(Day registeredDay, String comingTime) {
        String currentDate = registeredDay.getDayRegistered();
        String beginnPause = registeredDay.getBeginnPause();
        String endePause = registeredDay.getEndePause();
        String leavingTime = registeredDay.getLeavingTime();
        if (BinaryConditions.condition001(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate, comingTime, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition010(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate, comingTime, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition011(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,comingTime,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition100(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate, comingTime, beginnPause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition101(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,comingTime,beginnPause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition110(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,comingTime,beginnPause,endePause)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition111(beginnPause, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeTheOtherOnes(currentDate,comingTime,beginnPause,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }
    }

    public static void validateBeginnPause(Day registeredDay, String beginnPause) {
        String currentDate = registeredDay.getDayRegistered();
        String comingTime = registeredDay.getComingTime();
        String endePause = registeredDay.getEndePause();
        String leavingTime = registeredDay.getLeavingTime();
        if (BinaryConditions.condition001(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate, beginnPause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition010(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate, beginnPause, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition011(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate, beginnPause, endePause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition100(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isLastTimeNotAfterFirstTime(currentDate, comingTime,beginnPause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition101(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate, beginnPause, comingTime,leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition110(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate, beginnPause, comingTime, endePause)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition111(comingTime, endePause, leavingTime)) {
            if (IntervalValidator.isBeginnPausePailas(currentDate, beginnPause,comingTime,endePause, leavingTime)) {
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }
    }

    public static void validateEndePause(Day registeredDay, String endePause) {
        String currentDate = registeredDay.getDayRegistered();
        String comingTime = registeredDay.getComingTime();
        String beginnPause = registeredDay.getBeginnPause();
        String leavingTime = registeredDay.getLeavingTime();
        if (BinaryConditions.condition001(comingTime, beginnPause, leavingTime)) {
            if (IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition010(comingTime, beginnPause, leavingTime)) {
            if (IntervalValidator.isLastTimeNotAfterFirstTime(currentDate,beginnPause,endePause)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition011(comingTime, beginnPause, leavingTime)) {
            if (IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,endePause,beginnPause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition100(comingTime, beginnPause, leavingTime)) {
            if (IntervalValidator.isLastTimeNotAfterFirstTime(currentDate,comingTime,endePause)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition101(comingTime, beginnPause, leavingTime)) {
            if (IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,endePause,comingTime,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition110(comingTime, beginnPause, leavingTime)) {
            if (IntervalValidator.isLastTimeNotAfterOtherOneAndNotAfterFirstOne(currentDate,comingTime,beginnPause,endePause)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition111(comingTime, beginnPause, leavingTime)) {
            if (IntervalValidator.isEndePausePailas(currentDate,comingTime,beginnPause,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }
    }

    public static void validateLeavingTime(Day registeredDay, String leavingTime) {
        String currentDate = registeredDay.getDayRegistered();
        String comingTime = registeredDay.getComingTime();
        String beginnPause = registeredDay.getBeginnPause();
        String endePause = registeredDay.getEndePause();
        if (BinaryConditions.condition001(comingTime, beginnPause, endePause)) {

        } else if (BinaryConditions.condition010(comingTime, beginnPause, endePause)) {

        } else if (BinaryConditions.condition011(comingTime, beginnPause, endePause)) {

        } else if (BinaryConditions.condition100(comingTime, beginnPause, endePause)) {

        } else if (BinaryConditions.condition101(comingTime, beginnPause, endePause)) {

        } else if (BinaryConditions.condition110(comingTime, beginnPause, endePause)) {

        } else if (BinaryConditions.condition111(comingTime, beginnPause, endePause)) {

        }
    }
}
