package com.chilin.org.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntervalValidator {

    public static boolean isBeginPauseNotBeforeLeavingTime(String currentDate, String beginnPause, String leavingTime) {
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date leavingTimeComplete = createDateForTime(currentDate, leavingTime);
        return beginnPauseComlete.equals(leavingTimeComplete) ||
                beginnPauseComlete.after(leavingTimeComplete);
    }

    public static boolean isBeginPauseNotBeforeEndePause(String currentDate, String beginnPause, String endePause) {
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date endePauseComplete = createDateForTime(currentDate, endePause);
        return beginnPauseComlete.equals(endePauseComplete) ||
                beginnPauseComlete.after(endePauseComplete);
    }

    public static boolean isBeginnPauseNotBeforeEndePauseAndNotBeforeLeavingTime(String currentDate, String beginnPause, String endePause, String leavingTime) {
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date endePauseComplete = createDateForTime(currentDate, endePause);
        Date leavingTimeComplete = createDateForTime(currentDate,leavingTime);
        return beginnPauseComlete.after(endePauseComplete) ||
                beginnPauseComlete.equals(endePauseComplete) ||
                beginnPauseComlete.equals(leavingTimeComplete) ||
                beginnPauseComlete.after(leavingTimeComplete);
    }

    public static boolean isBeginnPauseNotAfterComingTime(String currentDate, String beginnPause, String comingTime) {
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        return beginnPauseComlete.equals(comingTimeComplete) ||
                beginnPauseComlete.before(comingTimeComplete);
    }

    public static boolean isBeginnPauseNotBetweenComingAndLeavingTime(String currentDate, String beginnPause, String comingTime, String leavingTime) {
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        Date beginnPauseComplete = createDateForTime(currentDate, beginnPause);
        Date leavingTimeComplete = createDateForTime(currentDate,leavingTime);
        return beginnPauseComplete.before(comingTimeComplete) ||
                beginnPauseComplete.equals(comingTimeComplete) ||
                beginnPauseComplete.equals(leavingTimeComplete) ||
                beginnPauseComplete.after(leavingTimeComplete);
    }

    public static boolean isBeginnPauseNotAfterComingTimeNorBeforeEndePause(String currentDate, String beginnPause, String comingTime, String endePause) {
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date endePauseComplete = createDateForTime(currentDate,endePause);
        return beginnPauseComlete.before(comingTimeComplete) ||
                beginnPauseComlete.equals(comingTimeComplete) ||
                beginnPauseComlete.equals(endePauseComplete) ||
                beginnPauseComlete.after(endePauseComplete);
    }

    public static boolean isBeginnPausePailas(String currentDate, String beginnPause,String comingTime, String endePause,String leavingTime) {
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
