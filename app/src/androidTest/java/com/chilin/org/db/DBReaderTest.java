package com.chilin.org.db;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    private void cleanData(String currentDate){
        dbWriter.deleteInfo(currentDate);
    }

}
