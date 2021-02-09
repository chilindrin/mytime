package com.chilin.org.stempeln;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;
import com.chilin.org.util.BinaryConditions;

public class InsertTimeValidator {

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
            if (IntervalValidator.isLastTimeNotAfterFirstTime(currentDate,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition010(comingTime, beginnPause, endePause)) {
            if (IntervalValidator.isLastTimeNotAfterFirstTime(currentDate,beginnPause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition011(comingTime, beginnPause, endePause)) {
            if (IntervalValidator.isLastTimeNotAfterOtherOneAndNotAfterFirstOne(currentDate,beginnPause,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition100(comingTime, beginnPause, endePause)) {
            if (IntervalValidator.isLastTimeNotAfterFirstTime(currentDate,comingTime,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition101(comingTime, beginnPause, endePause)) {
            if (IntervalValidator.isLastTimeNotAfterOtherOneAndNotAfterFirstOne(currentDate,comingTime,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition110(comingTime, beginnPause, endePause)) {
            if (IntervalValidator.isLastTimeNotAfterOtherOneAndNotAfterFirstOne(currentDate,comingTime,beginnPause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        } else if (BinaryConditions.condition111(comingTime, beginnPause, endePause)) {
            if (IntervalValidator.isLeavingTimePailas(currentDate,comingTime,beginnPause,endePause,leavingTime)){
                throw new MyTimeException("Das kannst du so nicht machen. Deine Pause ist nicht innerhalb der Arbeitszeit");
            }
        }
    }

}
