package com.chilin.org.util;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class TextProcessorTest {

    @Test
    public void testConversion(){
        String miPrueba = "30-03-2020,12:13,15:13|30-04-2020,12:13,15:13|30-05-2020,12:13,15:13";
        byte[] bytes = TextProcessor.convertStringToByteArray(miPrueba);
        String result = TextProcessor.convertByteArrayToString(bytes);
        MatcherAssert.assertThat(result, Matchers.is(miPrueba));
    }

    @Test
    public void getTime_MinutesAndHoursWithNoLeadingZeros_HourWithLeadingZerosForHoursAndMinutes(){
        int hours = 9;
        int minutes = 9;
        String time = TextProcessor.getTime(hours, minutes);
        MatcherAssert.assertThat(time, CoreMatchers.is("09:09"));
    }

    @Test
    public void getTime_TwoDigisMinutesAndHours_SameOutputAsInput(){
        int hours = 21;
        int minutes = 21;
        String time = TextProcessor.getTime(hours, minutes);
        MatcherAssert.assertThat(time, CoreMatchers.is("21:21"));
    }

    @Test
    public void getTwoDigitsNumber_OneDigitNumber_LeadingZeroInserted(){
        int input = 5;
        String twoDigitsNumber = TextProcessor.getTwoDigitsNumber(input);
        MatcherAssert.assertThat(twoDigitsNumber,CoreMatchers.is("05"));
    }

    @Test
    public void getTwoDigitsNumber_TwoDigitNumber_LeadingZeroInserted(){
        int input = 05;
        String twoDigitsNumber = TextProcessor.getTwoDigitsNumber(input);
        MatcherAssert.assertThat(twoDigitsNumber,CoreMatchers.is("05"));
    }

    @Test
    public void getTwoDigitsNumber_OneZero_TwoZeros(){
        int input = 0;
        String twoDigitsNumber = TextProcessor.getTwoDigitsNumber(input);
        MatcherAssert.assertThat(twoDigitsNumber,CoreMatchers.is("00"));
    }

    @Test
    public void getChosenDate_GivenInputFromDatePicker_StringIsFormatted(){
        int dayFromPicker = 2;
        int monthFromPicker = 9;
        int yearFromPicker = 2020;
        String result = TextProcessor.formatChosenDate(dayFromPicker,monthFromPicker,yearFromPicker);
        MatcherAssert.assertThat(result,CoreMatchers.is("02-09-2020"));
    }

}