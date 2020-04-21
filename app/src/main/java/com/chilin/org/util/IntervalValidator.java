package com.chilin.org.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntervalValidator {

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

    public static boolean isFirstTimeNotBeforeLastTime(String currentDate, String firstTime, String lastTime) {
        Date firstTimeComlete = createDateForTime(currentDate, firstTime);
        Date lastTimeComplete = createDateForTime(currentDate, lastTime);
        return firstTimeComlete.equals(lastTimeComplete) ||
                firstTimeComlete.after(lastTimeComplete);
    }

    public static boolean isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(String currentDate, String firstTime, String otherOne, String lastOne) {
        Date firstTimeComlete = createDateForTime(currentDate, firstTime);
        Date otherOneComplete = createDateForTime(currentDate, otherOne);
        Date lastOneComplete = createDateForTime(currentDate,lastOne);
        return firstTimeComlete.after(otherOneComplete) ||
                firstTimeComlete.equals(otherOneComplete) ||
                firstTimeComlete.equals(lastOneComplete) ||
                firstTimeComlete.after(lastOneComplete);
    }

    public static boolean isLastTimeNotAfterOtherOneAndNotAfterFirstOne(String currentDate, String firstTime, String otherOne, String lastOne) {
        Date firstTimeComlete = createDateForTime(currentDate, firstTime);
        Date otherOneComplete = createDateForTime(currentDate, otherOne);
        Date lastOneComplete = createDateForTime(currentDate,lastOne);
        return lastOneComplete.equals(otherOneComplete) ||
                lastOneComplete.before(otherOneComplete) ||
                lastOneComplete.equals(firstTimeComlete) ||
                lastOneComplete.before(firstTimeComlete);
    }

    public static boolean isFirstTimeNotBeforeTheOtherOnes(String currentDate,String firstTime,String otherTime,String laterTime,String lastTime){
        Date firstTimeComlete = createDateForTime(currentDate, firstTime);
        Date otherTimeComplete = createDateForTime(currentDate, otherTime);
        Date laterTimeComplete = createDateForTime(currentDate,laterTime);
        Date lastTimeComplete = createDateForTime(currentDate,lastTime);
        return firstTimeComlete.after(otherTimeComplete) ||
                firstTimeComlete.equals(otherTimeComplete) ||
                firstTimeComlete.equals(laterTimeComplete) ||
                firstTimeComlete.after(laterTimeComplete) ||
                firstTimeComlete.equals(lastTimeComplete) ||
                firstTimeComlete.after(lastTimeComplete);
    }

    public static boolean isGivenTimeNotBetweenFirstTimeAndLastTime(String currentDate,String givenTime,String firstTime,String lastTime){
        Date firstTimeComplete = createDateForTime(currentDate, firstTime);
        Date givenTimeComplete = createDateForTime(currentDate, givenTime);
        Date lastTimeComplete = createDateForTime(currentDate,lastTime);
        return givenTimeComplete.before(firstTimeComplete) ||
                givenTimeComplete.equals(firstTimeComplete) ||
                givenTimeComplete.equals(lastTimeComplete) ||
                givenTimeComplete.after(lastTimeComplete);
    }

    public static boolean isLastTimeNotAfterFirstTime(String currentDate, String firstTime,String lastTime) {
        Date firstTimeComplete = createDateForTime(currentDate, firstTime);
        Date lastTimeComlete = createDateForTime(currentDate, lastTime);
        return lastTimeComlete.equals(firstTimeComplete) ||
                lastTimeComlete.before(firstTimeComplete);
    }

    public static boolean isEndePausePailas(String currentDate, String comingTime, String beginnPause, String endePause, String leavingTime) {
        Date comingTimeComplete = createDateForTime(currentDate, comingTime);
        Date beginnPauseComlete = createDateForTime(currentDate, beginnPause);
        Date endePauseComplete = createDateForTime(currentDate, endePause);
        Date leavingTimeComplete = createDateForTime(currentDate, leavingTime);
        return endePauseComplete.after(leavingTimeComplete)
                || endePauseComplete.equals(leavingTimeComplete)
                || endePauseComplete.equals(beginnPauseComlete)
                || endePauseComplete.before(beginnPauseComlete)
                || endePauseComplete.equals(comingTimeComplete)
                || endePauseComplete.before(comingTimeComplete);
    }
}
