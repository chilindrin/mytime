package com.chilin.org.db.stempel;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.chilin.org.db.stempel.DBReader;
import com.chilin.org.db.stempel.DBWriter;
import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DBReaderTest {

    private DBReader sut;
    private DBWriter dbWriter;
    private Context applicationContext;

    @Before
    public void setUp(){
        applicationContext = ApplicationProvider.getApplicationContext();
        sut = new DBReader(applicationContext);
        dbWriter = new DBWriter(applicationContext);
    }

    @After
    public void tearDown(){
        applicationContext = null;
        sut = null;
        dbWriter = null;
    }

    @Test
    public void getRegisteredDay_OneRegisterForOneDayCreated_QueryReturnsThisDay(){
        // Given
        String currentDate = "24-12-2020";
        String comingTime = "09:10";
        cleanData(currentDate);

        dbWriter.createComingTime(currentDate,comingTime);

        // When
        Day registeredDay = sut.getRegisteredDay(currentDate);

        // Then
        MatcherAssert.assertThat(registeredDay.getDayRegistered(),CoreMatchers.notNullValue());
        MatcherAssert.assertThat(registeredDay.getDayRegistered(),CoreMatchers.is(currentDate));
        MatcherAssert.assertThat(registeredDay.getComingTime(),CoreMatchers.notNullValue());

        cleanData(currentDate);
    }

    @Test
    public void getRegisteredDay_TwoEntriesForOneDayCreated_ExceptionThrown(){
        // Given
        String currentDate = "24-12-2020";
        String comingTime = "09:10";
        cleanData(currentDate);

        dbWriter.createComingTime(currentDate,comingTime);
        dbWriter.createComingTime(currentDate,comingTime);
        boolean desiredExceptionWasThrown = false;

        try {
            // When
            sut.getRegisteredDay(currentDate);
        } catch (MyTimeException ex){
            desiredExceptionWasThrown = true;
        } finally {
            // Then
            MatcherAssert.assertThat(desiredExceptionWasThrown,CoreMatchers.is(true));
            cleanData(currentDate);
        }
    }

    @Test
    public void getRegisteredDay_NoEntriesForOneDayCreated_ReturnsNull(){
        // Given
        String currentDate = "24-12-2020";
        cleanData(currentDate);

        // When
        Day registeredDay = sut.getRegisteredDay(currentDate);

        // Then
        MatcherAssert.assertThat(registeredDay,CoreMatchers.nullValue());
    }

    @Test
    public void getDataFromMonth_3Days2ForDecember_Only2RegistriesFromDecember(){
        // Given
        String anotherDate = "01-11-2020";
        String oneDate = "24-12-2020";
        String secondDate = "25-12-2020";

        cleanData(anotherDate);
        cleanData(oneDate);
        cleanData(secondDate);

        dbWriter.createComingTime(anotherDate,"08:00");
        dbWriter.createComingTime(oneDate,"08:00");
        dbWriter.createComingTime(secondDate,"08:00");

        String expectedMonth = "12";
        String expectedYear = "2020";

        // When
        List<Day> workedDaysOfMonth = sut.getWorkedDaysOfMonth(expectedMonth,expectedYear);

        // Then
        MatcherAssert.assertThat(workedDaysOfMonth.size(),CoreMatchers.is(2));
        assertMonthResults(expectedMonth, expectedYear, workedDaysOfMonth);

        cleanData(anotherDate);
        cleanData(oneDate);
        cleanData(secondDate);
    }

    private void assertMonthResults(String expectedMonth, String expectedYear, List<Day> workedDaysOfMonth) {
        for (Day dayResult : workedDaysOfMonth){
            String registeredDate = dayResult.getDayRegistered();
            String[] registeredDateFields = registeredDate.split("-");
            String actualMonth = registeredDateFields[1];
            String actualYear = registeredDateFields[2];
            MatcherAssert.assertThat(actualMonth, CoreMatchers.is(expectedMonth));
            MatcherAssert.assertThat(actualYear,CoreMatchers.is(expectedYear));
        }
    }

    private void cleanData(String currentDate){
        dbWriter.deleteDay(currentDate);
    }

}
