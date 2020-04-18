package com.chilin.org.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class DateTimeOperationsProviderTest {

    @Test
    public void givenDayOfTheWeek_WhenMethodCalled_ThenTrueWillBeReturned(){
        LocalDateTime sundayForSure = LocalDateTime.of(2020, Month.MARCH,22,13,00);
        boolean weekend = DateTimeOperationsProvider.isWeekend(sundayForSure);
        MatcherAssert.assertThat(weekend, Matchers.is(true));
    }

    @Test
    public void createPause_8MinutenPause_0MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:08");
        MatcherAssert.assertThat(result,Matchers.is("0"));
    }

    @Test
    public void createPause_0MinutenPause_0MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:00");
        MatcherAssert.assertThat(result,Matchers.is("0"));
    }

    @Test
    public void createPause_5MinutenPause_0MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:05");
        MatcherAssert.assertThat(result,Matchers.is("0"));
    }

    @Test
    public void createPause_9MinutenPause_15MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:09");
        MatcherAssert.assertThat(result,Matchers.is("15"));
    }

    @Test
    public void createPause_20MinutenPause_15MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:20");
        MatcherAssert.assertThat(result,Matchers.is("15"));
    }

    @Test
    public void createPause_11MinutenPause_15MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:10","09:21");
        MatcherAssert.assertThat(result,Matchers.is("15"));
    }

    @Test
    public void createPause_21MinutenPause_30MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:21");
        MatcherAssert.assertThat(result,Matchers.is("30"));
    }

    @Test
    public void createPause_38MinutenPause_30MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:38");
        MatcherAssert.assertThat(result,Matchers.is("30"));
    }

    @Test
    public void createPause_26MinutenPause_30MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:10","09:36");
        MatcherAssert.assertThat(result,Matchers.is("30"));
    }

    @Test
    public void createPause_39MinutenPause_45MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:39");
        MatcherAssert.assertThat(result,Matchers.is("45"));
    }

    @Test
    public void createPause_50MinutenPause_45MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:50");
        MatcherAssert.assertThat(result,Matchers.is("45"));
    }

    @Test
    public void createPause_45MinutenPause_45MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:10","09:55");
        MatcherAssert.assertThat(result,Matchers.is("45"));
    }

    @Test
    public void createPause_51MinutenPause_60MinutenPause(){
        String result = DateTimeOperationsProvider.createPause("12-02-2020", "09:00","09:51");
        MatcherAssert.assertThat(result,Matchers.is("60"));
    }

    @Test
    public void pauseBeginnAfterComingTimeBeforeEndePauseAndBeforeLeavingTime_False() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:30";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnBeforeComingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:00";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustBeforeComingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:59";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnTheSameAsComingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:00";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterComingTimeBeforeEndePauseBeforeLeavingTime_False() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:01";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustBeforeEndePauseBeforeLeavingTime_False() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:34";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeAtTheSameTimeAsEndePauseBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:35";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustAfterEndePauseBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:36";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:38";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseJustBeforeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:39";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseAtTheSameTimeLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:40";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseJustAfterLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:41";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseAfterLeavingTime_True() {
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "19:00";
        String endePause = "18:35";
        String leavingTime = "18:40";
        boolean result = DateTimeOperationsProvider.isBeginnPausePailas(currentDate,comingTime,leavingTime,beginnPause, endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnBeforeComingTimeAndBeforeEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:00";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustBeforeComingTimeAndBeforeEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "17:59";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAtTheSameTimeAsComingTimeBeforeEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:00";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterComingTimeBeforeEndePauseNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:01";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeBeforeEndePauseNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:30";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustBeforeEndePauseNullLeavingTime_False(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:34";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAfterComingTimeTheSameAsEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:35";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeJustAfterEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "18:36";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterComingTimeAfterEndePauseNullLeavingTime_True(){
        String currentDate = "12-03-2020";
        String comingTime = "18:00";
        String beginnPause = "19:00";
        String endePause = "18:35";
        boolean result = DateTimeOperationsProvider.isBeginnPauseNotAfterComingTimeNorBeforeEndePause(currentDate,comingTime,beginnPause,endePause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnBeforeLeavingTimeNullComingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:00";
        String leavingTime = "18:30";
        boolean result = DateTimeOperationsProvider.isBeginnPauseAfterLeavingTime(currentDate,leavingTime,beginnPause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnJustBeforeLeavingTimeNullComingTime_False(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:29";
        String leavingTime = "18:30";
        boolean result = DateTimeOperationsProvider.isBeginnPauseAfterLeavingTime(currentDate,leavingTime,beginnPause);
        MatcherAssert.assertThat(result,Matchers.is(false));
    }

    @Test
    public void pauseBeginnAtTheSameTimeAsLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:30";
        String leavingTime = "18:30";
        boolean result = DateTimeOperationsProvider.isBeginnPauseAfterLeavingTime(currentDate,leavingTime,beginnPause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnJustAfterLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "18:31";
        String leavingTime = "18:30";
        boolean result = DateTimeOperationsProvider.isBeginnPauseAfterLeavingTime(currentDate,leavingTime,beginnPause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

    @Test
    public void pauseBeginnAfterLeavingTimeNullComingTime_True(){
        String currentDate = "12-03-2020";
        String beginnPause = "19:31";
        String leavingTime = "18:30";
        boolean result = DateTimeOperationsProvider.isBeginnPauseAfterLeavingTime(currentDate,leavingTime,beginnPause);
        MatcherAssert.assertThat(result,Matchers.is(true));
    }

}