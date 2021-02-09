package com.chilin.org.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;
import com.chilin.org.model.Month;

import org.apache.commons.lang3.StringUtils;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;

public class DateTimeOperator {

    public static final String PLATZ_HALTER = "<->";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFriendlyFormatCurrentDate(LocalDateTime currentDate){
        if (currentDate == null){
            throw new MyTimeException("Die aktuelle Zeit ist null");
        }
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
    public static String getWorkedTimeInDay(Day registeredDay){
        String comingTime = registeredDay.getComingTime();
        String beginnPause = registeredDay.getBeginnPause();
        String endePause = registeredDay.getEndePause();
        String leavingTime = registeredDay.getLeavingTime();
        String dayRegistered = registeredDay.getDayRegistered();
        if (StringUtils.isBlank(dayRegistered)
                || TextProcessor.isNotPresent(comingTime)
                || TextProcessor.isNotPresent(leavingTime)){
            return PLATZ_HALTER;
        }
        if (TextProcessor.isPresent(beginnPause) && TextProcessor.isPresent(endePause)){
            double workedTimeInDay = calculateWorkedTimeWithPause(comingTime, beginnPause, endePause, leavingTime, dayRegistered);
            return String.valueOf(workedTimeInDay);
        }
        if (TextProcessor.isNotPresent(beginnPause) || TextProcessor.isNotPresent(endePause)){
            double workedHours = calculateIntervalDurationInHours(dayRegistered,comingTime,leavingTime);
            return String.valueOf(workedHours);
        }
        return PLATZ_HALTER;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static double calculateWorkedTimeWithPause(String comingTime, String beginnPause, String endePause, String leavingTime, String dayRegistered) {
        double pauseDuration = calculateIntervalDurationInHours(dayRegistered,beginnPause,endePause);
        double workedHours = calculateIntervalDurationInHours(dayRegistered,comingTime,leavingTime);
        if (pauseDuration > workedHours){
            throw new MyTimeException("Es kann nicht sein, dass die Pause länger als die gearbeitete Zeit ist");
        }
        return workedHours - pauseDuration;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Month getWorkingTimeInMonth(List<Day> workingDaysPerMonth) {
        Month month = new Month();
        month.setMonthlyWorkedTimeIST(getMonthlyWorkedTimeIST(workingDaysPerMonth));
        month.setMonthlyWorkedTimeSOLL(getMonthlyWorkedTimeSOLL(workingDaysPerMonth));
        return month;
    }

    private static String getMonthlyWorkedTimeSOLL(List<Day> workingDaysPerMonth) {
        int monthlyWorkedTimeSOLLInt = workingDaysPerMonth.size() * 8;
        return String.valueOf(monthlyWorkedTimeSOLLInt);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getMonthlyWorkedTimeIST(List<Day> workedDaysPerMonth){
        double totalAmountOfTime = 0L;
        for (Day workedDay:workedDaysPerMonth) {
            String workedTimeInDay = getWorkedTimeInDay(workedDay);
            if (PLATZ_HALTER.equals(workedTimeInDay)){
                continue;
            }
            double workedTimeInDayDouble = Double.parseDouble(workedTimeInDay);
            totalAmountOfTime = totalAmountOfTime + workedTimeInDayDouble;
        }
        return String.valueOf(totalAmountOfTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String createPause(String currentDay, String beginnPause, String endePause){
        if (TextProcessor.isNotPresent(currentDay) || TextProcessor.isNotPresent(beginnPause) || TextProcessor.isNotPresent(endePause)){
            return PLATZ_HALTER;
        }
        double pauseInMinutes = calculateIntervalDurationInHours(currentDay, beginnPause, endePause);
        return String.valueOf(pauseInMinutes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static double calculateIntervalDurationInHours(String currentDay, String beginnTime, String endeTime) {
        String beginnTimeWithCurrentDay = joinDateWithTime(currentDay,beginnTime);
        String endeTimeWithCurrentDay = joinDateWithTime(currentDay,endeTime);

        DateTimeFormatter currentDayWithTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timeStart = LocalDateTime.parse(beginnTimeWithCurrentDay,currentDayWithTimeFormatter);
        LocalDateTime timeEnde = LocalDateTime.parse(endeTimeWithCurrentDay,currentDayWithTimeFormatter);

        Duration intervalDuration = Duration.between(timeStart,timeEnde);
        long intervalInSeconds = intervalDuration.getSeconds();
        double numerador = intervalInSeconds;
        return numerador / 3600;
    }

    private static String joinDateWithTime(String registeredDay,String time){
        return registeredDay + " " + time;
    }

    public static String convertMonth(String selectedMonth) {
        String monthQuery = "";
        switch (selectedMonth){
            case "Januar":
                monthQuery = "01";
                break;
            case "Februar":
                monthQuery = "02";
                break;
            case "Maerz":
                monthQuery = "03";
                break;
            case "April":
                monthQuery = "04";
                break;
            case "Mai":
                monthQuery = "05";
                break;
            case "Juni":
                monthQuery = "06";
                break;
            case "Juli":
                monthQuery = "07";
                break;
            case "August":
                monthQuery = "08";
                break;
            case "September":
                monthQuery = "09";
                break;
            case "Oktober":
                monthQuery = "10";
                break;
            case "November":
                monthQuery = "11";
                break;
            case "Dezember":
                monthQuery = "12";
                break;
        }
        return monthQuery;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String calculateFeierabend(Day registeredDay) {
        if (TextProcessor.isNotPresent(registeredDay.getComingTime())){
            return "<brauche mehr Daten>";
        }
        if (TextProcessor.isPresent(registeredDay.getLeavingTime())){
            return "<du bist schon zuhause>";
        }
        if (TextProcessor.isPresent(registeredDay.getBeginnPause())
                && TextProcessor.isPresent(registeredDay.getEndePause())){
            return calculateFeierabendWithPause(registeredDay);
        }
        return calculateFeierabendWithoutPause(registeredDay);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String calculateFeierabendWithPause(Day registeredDay) {
        double pauseDuration = calculateIntervalDurationInHours(registeredDay.getDayRegistered(),
                registeredDay.getBeginnPause(), registeredDay.getEndePause());
        long hoursPerDay = 8l;
        long totalAmount = (long) (hoursPerDay + pauseDuration);
        String comingTimeWithCurrentDay = joinDateWithTime(registeredDay.getDayRegistered(),registeredDay.getComingTime());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timeStart = LocalDateTime.parse(comingTimeWithCurrentDay,dateFormat);
        LocalDateTime feierAbendLocalDate = timeStart.plusHours(totalAmount);
        String feierAbendHourString = TextProcessor.getTwoDigitsNumber(feierAbendLocalDate.getHour());
        String feierAbendMinuteString = TextProcessor.getTwoDigitsNumber(feierAbendLocalDate.getMinute());
        return feierAbendHourString + ":" + feierAbendMinuteString;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String calculateFeierabendWithoutPause(Day registeredDay){
        String comingTimeWithCurrentDay = joinDateWithTime(registeredDay.getDayRegistered(),registeredDay.getComingTime());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timeStart = LocalDateTime.parse(comingTimeWithCurrentDay,dateFormat);
        LocalDateTime feierAbendLocalDate = timeStart.plusHours(8l);
        String feierAbendHourString = TextProcessor.getTwoDigitsNumber(feierAbendLocalDate.getHour());
        String feierAbendMinuteString = TextProcessor.getTwoDigitsNumber(feierAbendLocalDate.getMinute());
        return feierAbendHourString + ":" + feierAbendMinuteString;
    }

}
