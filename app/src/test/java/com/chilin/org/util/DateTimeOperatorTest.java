package com.chilin.org.util;

import com.chilin.org.model.Day;
import com.google.api.client.util.DateTime;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTimeOperatorTest {

    @Test
    public void givenDayOfTheWeek_WhenMethodCalled_ThenTrueWillBeReturned(){
        LocalDateTime sundayForSure = LocalDateTime.of(2020, Month.MARCH,22,13,00);
        boolean weekend = DateTimeOperator.isWeekend(sundayForSure);
        MatcherAssert.assertThat(weekend, Matchers.is(true));
    }

    @Test
    public void createPause_0MinutenPause_0MinutenPause(){
        String result = DateTimeOperator.createPause("12-02-2020", "09:00","09:00");
        MatcherAssert.assertThat(result,Matchers.is("0.0"));
    }

    @Test
    public void createPause_15MinutenPause(){
        String result = DateTimeOperator.createPause("12-02-2020", "09:00","09:15");
        MatcherAssert.assertThat(result,Matchers.is("0.25"));
    }

    @Test
    public void createPause_When9_930_Then_30MinutenPause(){
        String result = DateTimeOperator.createPause("12-02-2020", "09:00","09:30");
        MatcherAssert.assertThat(result,Matchers.is("0.5"));
    }

    @Test
    public void createPause_When9_945_Then_45MinutenPause(){
        String result = DateTimeOperator.createPause("12-02-2020", "09:00","09:45");
        MatcherAssert.assertThat(result,Matchers.is("0.75"));
    }

    @Test
    public void createPause_60MinutenPause(){
        String result = DateTimeOperator.createPause("12-02-2020", "09:00","10:00");
        MatcherAssert.assertThat(result,Matchers.is("1.0"));
    }

    @Test
    public void createPause_30MinutenPause(){
        String result = DateTimeOperator.createPause("12-02-2020", "08:45","09:15");
        MatcherAssert.assertThat(result,Matchers.is("0.5"));
    }

    @Test
    public void createPause_45MinutenPause(){
        String result = DateTimeOperator.createPause("12-02-2020", "08:45","09:30");
        MatcherAssert.assertThat(result,Matchers.is("0.75"));
    }

    @Test
    public void createPause_Given_NoEndPause_Then_SpecialString(){
        String result = DateTimeOperator.createPause("12-02-2020", "08:45","");
        MatcherAssert.assertThat(result,Matchers.is("<->"));
    }

    @Test
    public void createPause_Given_NullEndPause_Then_SpecialString(){
        String result = DateTimeOperator.createPause("12-02-2020", "08:45",null);
        MatcherAssert.assertThat(result,Matchers.is("<->"));
    }

    @Test
    public void createPause_Given_EntireWorkedDay_Then_8(){
        String result = DateTimeOperator.createPause("12-02-2020", "08:00","16:00");
        MatcherAssert.assertThat(result,Matchers.is("8.0"));
    }

    @Test
    public void getWorkedTimeInDay_3WorkingHours30MinPause_2_5HoursWorkingTime(){
        Day day = new Day();
        day.setDayRegistered("12-02-2020");
        day.setComingTime("08:00");
        day.setBeginnPause("09:00");
        day.setEndePause("09:30");
        day.setLeavingTime("11:00");
        String workedTimeInDay = DateTimeOperator.getWorkedTimeInDay(day);
        MatcherAssert.assertThat(workedTimeInDay,Matchers.is("2.5"));
    }

    @Test
    public void getWorkedTimeInMonth(){
        Day day = new Day();
        day.setDayRegistered("12-02-2020");
        day.setComingTime("08:00");
        day.setBeginnPause("09:00");
        day.setEndePause("09:30");
        day.setLeavingTime("11:00");

        Day dayDos = new Day();
        dayDos.setDayRegistered("13-02-2020");
        dayDos.setComingTime("08:00");
        dayDos.setBeginnPause("09:00");
        dayDos.setEndePause("09:30");
        dayDos.setLeavingTime("11:00");

        List<Day> workingDaysPerMonth = new ArrayList<>();
        workingDaysPerMonth.add(day);
        workingDaysPerMonth.add(dayDos);

        com.chilin.org.model.Month monthResult = DateTimeOperator.getWorkingTimeInMonth(workingDaysPerMonth);

        MatcherAssert.assertThat(monthResult.getMonthlyWorkedTimeIST(),Matchers.is("5.0"));
        MatcherAssert.assertThat(monthResult.getMonthlyWorkedTimeSOLL(),Matchers.is("16"));
    }

    @Test
    public void getWorkedTimeInMonth_OneDayDamaged(){
        Day day = new Day();
        day.setDayRegistered("12-02-2020");
        day.setBeginnPause("09:00");
        day.setEndePause("09:30");
        day.setLeavingTime("11:00");

        Day dayDos = new Day();
        dayDos.setDayRegistered("13-02-2020");
        dayDos.setComingTime("08:00");
        dayDos.setBeginnPause("09:00");
        dayDos.setEndePause("09:30");
        dayDos.setLeavingTime("11:00");

        List<Day> workingDaysPerMonth = new ArrayList<>();
        workingDaysPerMonth.add(day);
        workingDaysPerMonth.add(dayDos);

        com.chilin.org.model.Month resultMonth = DateTimeOperator.getWorkingTimeInMonth(workingDaysPerMonth);
        MatcherAssert.assertThat(resultMonth.getMonthlyWorkedTimeIST(),Matchers.is("2.5"));
        MatcherAssert.assertThat(resultMonth.getMonthlyWorkedTimeSOLL(),Matchers.is("16"));
    }

    @Test
    public void getWorkedTimeInMonth_ZeroWorkedDays(){
        List<Day> workingDaysPerMonth = new ArrayList<>();

        com.chilin.org.model.Month resultMonth = DateTimeOperator.getWorkingTimeInMonth(workingDaysPerMonth);
        MatcherAssert.assertThat(resultMonth.getMonthlyWorkedTimeIST(),Matchers.is("0.0"));
        MatcherAssert.assertThat(resultMonth.getMonthlyWorkedTimeSOLL(),Matchers.is("0"));
    }

    @Test
    public void getWorkedTimeInDay_NoComingTime_NoCalculationOfWorkingTime(){
        Day day = new Day();
        day.setDayRegistered("12-02-2020");
        day.setComingTime("");
        day.setBeginnPause("09:00");
        day.setEndePause("09:30");
        day.setLeavingTime("11:00");
        String workedTimeInDay = DateTimeOperator.getWorkedTimeInDay(day);
        MatcherAssert.assertThat(workedTimeInDay,Matchers.is("<->"));

    }

    @Test
    public void getWorkedTimeInDay_NoRegisteredDayNoComingTime_NoCalculationOfWorkingTime(){
        Day day = new Day();
        day.setComingTime("");
        day.setBeginnPause("09:00");
        day.setEndePause("09:30");
        day.setLeavingTime("11:00");
        String workedTimeInDay = DateTimeOperator.getWorkedTimeInDay(day);
        MatcherAssert.assertThat(workedTimeInDay,Matchers.is("<->"));
    }

    @Test
    public void getWorkedTimeInDay_CompleteDay_NormalCalculationOfWorkingTime(){
        Day day = new Day();
        day.setDayRegistered("12-02-2020");
        day.setComingTime("08:00");
        day.setBeginnPause("09:00");
        day.setEndePause("09:30");
        day.setLeavingTime("11:00");
        String workedTimeInDay = DateTimeOperator.getWorkedTimeInDay(day);
        MatcherAssert.assertThat(workedTimeInDay,Matchers.is("2.5"));
    }

    @Test
    public void getWorkedTimeInDay_NoBeginnPause_CalculationOfWorkingTimeWithoutPause(){
        Day day = new Day();
        day.setDayRegistered("12-02-2020");
        day.setComingTime("08:00");
        day.setEndePause("09:30");
        day.setLeavingTime("11:00");
        String workedTimeInDay = DateTimeOperator.getWorkedTimeInDay(day);
        MatcherAssert.assertThat(workedTimeInDay,Matchers.is("3.0"));
    }

    @Test
    public void getWorkedTimeInDay_NoEndePause_CalculationOfWorkingTimeWithoutPause(){
        Day day = new Day();
        day.setDayRegistered("12-02-2020");
        day.setComingTime("08:00");
        day.setBeginnPause("09:00");
        day.setLeavingTime("11:00");
        String workedTimeInDay = DateTimeOperator.getWorkedTimeInDay(day);
        MatcherAssert.assertThat(workedTimeInDay,Matchers.is("3.0"));
    }

    @Test
    public void calculateFeierabend() {
        Day registeredDay = new Day();
        registeredDay.setComingTime("08:00");
        registeredDay.setDayRegistered("29-06-2020");

        String feierabendTime = DateTimeOperator.calculateFeierabend(registeredDay);

        MatcherAssert.assertThat(feierabendTime, CoreMatchers.is("16:00"));
    }

    @Test
    public void calculateFeierabend_withPause() {
        Day registeredDay = new Day();
        registeredDay.setComingTime("08:00");
        registeredDay.setBeginnPause("09:00");
        registeredDay.setEndePause("10:00");
        registeredDay.setDayRegistered("29-06-2020");

        String feierabendTime = DateTimeOperator.calculateFeierabend(registeredDay);

        MatcherAssert.assertThat(feierabendTime, CoreMatchers.is("17:00"));
    }

}