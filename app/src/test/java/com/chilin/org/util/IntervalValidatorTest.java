package com.chilin.org.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class IntervalValidatorTest {

    @Test
    public void pauseBeginnAfterComingTimeBeforeEndePauseAndBeforeLeavingTime_False() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:30";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,beginnPause,comingTime,endePause,leavingTime);
        MatcherAssert.assertThat(result, Matchers.is(false));
    }

    @Test
    public void pauseBeginnBeforeComingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:00";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustBeforeComingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:59";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnTheSameAsComingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:00";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterComingTimeBeforeEndePauseBeforeLeavingTime_False() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:01";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,beginnPause,comingTime,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustBeforeEndePauseBeforeLeavingTime_False() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:34";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,beginnPause,comingTime,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeAtTheSameTimeAsEndePauseBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:35";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustAfterEndePauseBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:36";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:38";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseJustBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:39";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseAtTheSameTimeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:40";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseJustAfterLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:41";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseAfterLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "19:00";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = IntervalValidator.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnBeforeComingTimeAndBeforeEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:00";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,beginnPause,comingTime,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustBeforeComingTimeAndBeforeEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:59";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,beginnPause,comingTime,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAtTheSameTimeAsComingTimeBeforeEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:00";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterComingTimeBeforeEndePauseNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:01";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,beginnPause,comingTime,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeBeforeEndePauseNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:30";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,beginnPause,comingTime,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustBeforeEndePauseNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:34";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,beginnPause,comingTime,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeTheSameAsEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:35";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustAfterEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:36";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "19:00";
        String endePause = "18:35";
        boolean result = IntervalValidator.isGivenTimeNotBetweenFirstTimeAndLastTime(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnBeforeEndePauseAndBeforeLeavingTimeNullComingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:00";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnJustBeforeEndePauseAndBeforeLeavingTimeNullComingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:34";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnTheSameAsEndePauseAndBeforeLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:35";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterEndePauseAndBeforeLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:36";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterEndePauseAndBeforeLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:45";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterEndePauseJustBeforeLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:59";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterEndePauseTheSameAsLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "19:00";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnBeforeComingTimeNullEndePauseAndNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:30";
        String comingTime = "19:00";
        boolean result = IntervalValidator.isBeginnPauseNotAfterComingTime(currentDate,beginnPause,comingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustBeforeComingTimeNullEndePauseAndNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:59";
        String comingTime = "19:00";
        boolean result = IntervalValidator.isBeginnPauseNotAfterComingTime(currentDate,beginnPause,comingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnTheSameAsComingTimeNullEndePauseAndNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "19:00";
        String comingTime = "19:00";
        boolean result = IntervalValidator.isBeginnPauseNotAfterComingTime(currentDate,beginnPause,comingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterComingTimeNullEndePauseAndNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "19:01";
        String comingTime = "19:00";
        boolean result = IntervalValidator.isBeginnPauseNotAfterComingTime(currentDate,beginnPause,comingTime);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeNullEndePauseAndNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "19:30";
        String comingTime = "19:00";
        boolean result = IntervalValidator.isBeginnPauseNotAfterComingTime(currentDate,beginnPause,comingTime);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterEndePauseJustAfterLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "19:01";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterEndePauseAfterLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "19:45";
        String endePause = "18:35";
        String leavingTime = "19:00";
        boolean result = IntervalValidator.isFirstTimeNotBeforeOtherOneAndNotBeforeLastOne(currentDate,beginnPause,endePause,leavingTime);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnBeforeEndePauseNullComingTimeNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:00";
        String endePause = "18:30";
        boolean result = IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnJustBeforeEndePauseNullComingTimeNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:29";
        String endePause = "18:30";
        boolean result = IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAtTheSameTimeAsEndePauseNullComingTimeNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:30";
        String endePause = "18:30";
        boolean result = IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterEndePauseNullComingTimeNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:31";
        String endePause = "18:30";
        boolean result = IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterEndePauseNullComingTimeNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:45";
        String endePause = "18:30";
        boolean result = IntervalValidator.isFirstTimeNotBeforeLastTime(currentDate,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

}