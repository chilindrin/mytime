package com.chilin.org.db.stempel;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.chilin.org.db.stempel.DBReader;
import com.chilin.org.db.stempel.DBWriter;
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

    @Test
    public void createComingTime_test_test(){
        // Given
        String currentDate = "24-12-2020";
        cleanData(currentDate);

        Day registeredDay = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay,CoreMatchers.is(CoreMatchers.nullValue()));

        // When
        String comingTime = "09:10";
        sut.createComingTime(currentDate,comingTime);

        Day registeredDayAfter = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDayAfter,CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(registeredDayAfter.getComingTime(),CoreMatchers.is(comingTime));

        cleanData(currentDate);
    }

    @Test
    public void deleteDay_DateToDelete_DayDoesNotExistInDBAnyMore(){
        // Given
        String currentDate = "24-12-2020";
        cleanData(currentDate);

        Day registeredDay = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay,CoreMatchers.is(CoreMatchers.nullValue()));

        Day dayToDelete = new Day();
        dayToDelete.setDayRegistered(currentDate);
        dayToDelete.setComingTime("8:00");
        dayToDelete.setBeginnPause("9:00");
        dayToDelete.setEndePause("9:15");
        dayToDelete.setLeavingTime("17:00");

        sut.createDay(dayToDelete);

        Day createdDayToDelete = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(createdDayToDelete,CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(createdDayToDelete.getDayRegistered(),CoreMatchers.is(currentDate));

        // When
        sut.deleteDay(currentDate);

        // Then
        Day resultDelete = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(resultDelete,CoreMatchers.is(CoreMatchers.nullValue()));

        cleanData(currentDate);
    }

    @Test
    public void changeDay_OldValues_ValuesAreChanged(){
        // Given
        String currentDate = "24-12-2020";
        cleanData(currentDate);

        Day registeredDay = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(registeredDay,CoreMatchers.is(CoreMatchers.nullValue()));

        Day oldValues = new Day();
        oldValues.setDayRegistered(currentDate);
        oldValues.setComingTime("8:00");
        oldValues.setBeginnPause("9:00");
        oldValues.setEndePause("9:15");
        oldValues.setLeavingTime("17:00");

        sut.createDay(oldValues);

        Day oldValuesSaved = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(oldValuesSaved,CoreMatchers.is(CoreMatchers.notNullValue()));

        Day newValues = new Day();
        newValues.setDayRegistered(currentDate);
        // NEW VALUE
        newValues.setComingTime("8:30");
        newValues.setBeginnPause("9:00");
        newValues.setEndePause("9:15");
        newValues.setLeavingTime("17:00");

        // When
        int rowsAffected = sut.updateDay(newValues);

        MatcherAssert.assertThat(rowsAffected,CoreMatchers.is(1));

        Day newValuesSaved = dbReader.getRegisteredDay(currentDate);
        MatcherAssert.assertThat(newValuesSaved.getComingTime(),CoreMatchers.is("8:30"));

        cleanData(currentDate);
    }

    private void cleanData(String currentDate){
        sut.deleteDay(currentDate);
    }
}