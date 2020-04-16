package com.chilin.org.db;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.chilin.org.model.Day;
import com.chilin.org.util.DateTimeOperationsProvider;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
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
        cleanData();

        LocalDateTime currentTime = DateTimeOperationsProvider.getCurrentDate();
        String currentDate = DateTimeOperationsProvider.getFriendlyFormatCurrentDate(currentTime);
        dbWriter.createComingTime(currentDate);

        // When
        Day registeredDay = sut.getRegisteredDay(currentDate);

        // Then
        MatcherAssert.assertThat(registeredDay.getDayRegistered(),CoreMatchers.notNullValue());
        MatcherAssert.assertThat(registeredDay.getDayRegistered(),CoreMatchers.is(currentDate));
        MatcherAssert.assertThat(registeredDay.getComingTime(),CoreMatchers.notNullValue());

        cleanData();
    }

    @Test
    public void getRegisteredDay_TwoEntriesForOneDayCreated_ExceptionThrown(){
        // Given
        cleanData();

        LocalDateTime currentTime = DateTimeOperationsProvider.getCurrentDate();
        String currentDate = DateTimeOperationsProvider.getFriendlyFormatCurrentDate(currentTime);
        dbWriter.createComingTime(currentDate);
        dbWriter.createComingTime(currentDate);
        boolean desiredExceptionWasThrown = false;

        try {
            // When
            sut.getRegisteredDay(currentDate);
        } catch (IllegalStateException ex){
            desiredExceptionWasThrown = true;
        } finally {
            // Then
            MatcherAssert.assertThat(desiredExceptionWasThrown,CoreMatchers.is(true));
            cleanData();
        }
    }

    private void cleanData(){
        dbWriter.deleteInfo();
        List<String[]> allDataInDB = sut.getAllDataInDB();
        MatcherAssert.assertThat(allDataInDB.size(), CoreMatchers.is(0));
    }

}
