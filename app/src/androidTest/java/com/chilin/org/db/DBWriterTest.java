package com.chilin.org.db;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.chilin.org.model.Day;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DBWriterTest {

    private DBReader dbReader;
    private DBWriter sut;
    private Context applicationContext;

    @Before
    public void setUp(){
        applicationContext = ApplicationProvider.getApplicationContext();
        dbReader = new DBReader(applicationContext);
        sut = new DBWriter(applicationContext);
    }

    @After
    public void tearDown(){
        applicationContext = null;
        dbReader = null;
        sut = null;
    }

    @Test
    public void createBeginnPause_BeginnPauseAndCurrentDate_BeginnPauseCreated() {
        // Given
        String currentDate = "24-12-2020";
        cleanData(currentDate);

        // When
        String beginnPause = "09:10";
        sut.createBeginnPause(currentDate,beginnPause);

        // Then
        Day registeredDay = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay,CoreMatchers.notNullValue());
        MatcherAssert.assertThat(registeredDay.getDayRegistered(),CoreMatchers.is(currentDate));
        MatcherAssert.assertThat(registeredDay.getBeginnPause(),CoreMatchers.is(beginnPause));

        cleanData(currentDate);
    }

    @Test
    public void updateBeginnPause_RegisteredDayWithNullBeginnPause_BeginnPauseCreated() {
        // Given
        String currentDate = "24-12-2020";
        cleanData(currentDate);

        String beginnPause = null;
        sut.createBeginnPause(currentDate,beginnPause);

        Day registeredDay1 = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay1.getDayRegistered(),CoreMatchers.is(currentDate));
        MatcherAssert.assertThat(registeredDay1.getBeginnPause(),CoreMatchers.nullValue());

        // When
        String beginnPauseNotNull = "09:10";
        sut.updateBeginnPause(currentDate,beginnPauseNotNull);

        // Then
        Day registeredDay2 = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay2.getDayRegistered(),CoreMatchers.is(currentDate));
        MatcherAssert.assertThat(registeredDay2.getBeginnPause(),CoreMatchers.is(beginnPauseNotNull));

        cleanData(currentDate);
    }

    @Test
    public void updateBeginnPause_RegisteredDayWithOldBeginnPause_BeginnPauseUpdated() {
        // Given
        String currentDate = "24-12-2020";
        cleanData(currentDate);

        String beginnPause = "9:00";
        sut.createBeginnPause(currentDate,beginnPause);

        Day registeredDay1 = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay1.getDayRegistered(),CoreMatchers.is(currentDate));
        MatcherAssert.assertThat(registeredDay1.getBeginnPause(),CoreMatchers.is(beginnPause));

        // When
        String updatedBeginnPause = "09:10";
        sut.updateBeginnPause(currentDate,updatedBeginnPause);

        // Then
        Day registeredDay2 = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay2.getDayRegistered(),CoreMatchers.is(currentDate));
        MatcherAssert.assertThat(registeredDay2.getBeginnPause(),CoreMatchers.is(updatedBeginnPause));

        cleanData(currentDate);
    }

    private void cleanData(String currentDate){
        sut.deleteInfo(currentDate);
    }
}