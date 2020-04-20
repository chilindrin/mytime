package com.chilin.org.db;

import android.content.Context;

import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

import org.junit.Test;
import org.mockito.Mockito;

public class TimeRegisterTest {

    private TimeRegister sut;

    @Test
    public void saveBeginnPause_NoRegistryForCurrentDate_CreateBeginnPauseMethodCalled() {
        // Given
        Context contextMock = Mockito.mock(Context.class);
        sut = new TimeRegister(contextMock);
        DBReader dbReaderStub = Mockito.mock(DBReader.class);
        DBWriter dbWriterMock = Mockito.mock(DBWriter.class);
        sut.setDbReader(dbReaderStub);
        sut.setDbWriter(dbWriterMock);

        String currentDate = "12-04-2020";
        Mockito.doReturn(null).when(dbReaderStub).getRegisteredDay(currentDate);

        String beginnPause = "09:10";
        // When
        sut.saveBeginnPause(currentDate,beginnPause);
        // Then
        Mockito.verify(dbWriterMock, Mockito.times(1)).createBeginnPause(currentDate, beginnPause);
    }

    @Test
    public void saveBeginnPause_DateRegisteredInDBButNorCommingLeavingTime_UpdateBeginnPauseMethodCalled() {
        // Given
        Context contextMock = Mockito.mock(Context.class);
        sut = new TimeRegister(contextMock);
        DBReader dbReaderStub = Mockito.mock(DBReader.class);
        DBWriter dbWriterMock = Mockito.mock(DBWriter.class);
        sut.setDbReader(dbReaderStub);
        sut.setDbWriter(dbWriterMock);

        String currentDate = "12-04-2020";

        Day registeredDay = new Day();
        registeredDay.setDayRegistered(currentDate);
        Mockito.doReturn(registeredDay).when(dbReaderStub).getRegisteredDay(currentDate);

        // When
        String beginnPause = "09:10";
        sut.saveBeginnPause(currentDate,beginnPause);

        Mockito.verify(dbWriterMock,Mockito.times(1)).updateBeginnPause(currentDate,beginnPause);
    }

    @Test
    public void saveBeginnPause_DateRegisteredInDBButBeginPauseDoesNotMatchCondition_UpdateMethodNotCalled() {
        // Given
        Context contextMock = Mockito.mock(Context.class);
        sut = new TimeRegister(contextMock);
        DBReader dbReaderStub = Mockito.mock(DBReader.class);
        DBWriter dbWriterMock = Mockito.mock(DBWriter.class);
        sut.setDbReader(dbReaderStub);
        sut.setDbWriter(dbWriterMock);

        String currentDate = "12-04-2020";

        Day registeredDay = new Day();
        registeredDay.setComingTime("09:00");
        registeredDay.setEndePause("9:05");
        registeredDay.setLeavingTime("09:30");
        registeredDay.setDayRegistered(currentDate);
        Mockito.doReturn(registeredDay).when(dbReaderStub).getRegisteredDay(currentDate);

        // When
        String beginnPause = "09:10";
        try {
            sut.saveBeginnPause(currentDate,beginnPause);
        } catch (MyTimeException e){}

        Mockito.verify(dbWriterMock,Mockito.times(0)).updateBeginnPause(currentDate,beginnPause);
    }

    @Test
    public void saveComingTime_EmptyAlreadyExistingRegisteredDay_UpdateMethodCalled(){
        // Given
        String currentDate = "12-04-2020";
        String comingTime = "8:00";
        DBReader dbReaderStub = Mockito.mock(DBReader.class);
        DBWriter dbWriterMock = Mockito.mock(DBWriter.class);

        Context contextStub = Mockito.mock(Context.class);

        sut = new TimeRegister(contextStub);

        sut.setDbReader(dbReaderStub);
        sut.setDbWriter(dbWriterMock);

        Day registeredDay = new Day();
        Mockito.doReturn(registeredDay).when(dbReaderStub).getRegisteredDay(currentDate);

        // When
        sut.saveComingTime(currentDate,comingTime);

        // Then
        Mockito.verify(dbWriterMock,Mockito.times(1)).updateComingTime(currentDate,comingTime);
    }

    @Test
    public void saveComingTime_RegisteredDayWithAllFields_UpdateMethodCalled(){
        // Given
        String currentDate = "12-04-2020";
        String comingTime = "08:00";
        DBReader dbReaderStub = Mockito.mock(DBReader.class);
        DBWriter dbWriterMock = Mockito.mock(DBWriter.class);

        Context contextStub = Mockito.mock(Context.class);

        sut = new TimeRegister(contextStub);

        sut.setDbReader(dbReaderStub);
        sut.setDbWriter(dbWriterMock);

        Day registeredDay = new Day();
        registeredDay.setDayRegistered(currentDate);
        registeredDay.setBeginnPause("09:00");
        registeredDay.setEndePause("09:30");
        registeredDay.setLeavingTime("16:00");

        Mockito.doReturn(registeredDay).when(dbReaderStub).getRegisteredDay(currentDate);

        // When
        sut.saveComingTime(currentDate,comingTime);

        // Then
        Mockito.verify(dbWriterMock,Mockito.times(1)).updateComingTime(currentDate,comingTime);
    }

    @Test(expected = MyTimeException.class)
    public void saveComingTime_WrongComingTimeToSave_ExceptionThrown(){
        // Given
        String currentDate = "12-04-2020";
        String comingTime = "09:15";
        DBReader dbReaderStub = Mockito.mock(DBReader.class);
        DBWriter dbWriterMock = Mockito.mock(DBWriter.class);

        Context contextStub = Mockito.mock(Context.class);

        sut = new TimeRegister(contextStub);

        sut.setDbReader(dbReaderStub);
        sut.setDbWriter(dbWriterMock);

        Day registeredDay = new Day();
        registeredDay.setDayRegistered(currentDate);
        registeredDay.setBeginnPause("09:00");
        registeredDay.setEndePause("09:30");
        registeredDay.setLeavingTime("16:00");

        Mockito.doReturn(registeredDay).when(dbReaderStub).getRegisteredDay(currentDate);

        // When
        sut.saveComingTime(currentDate,comingTime);
    }

    @Test
    public void saveComingTime_NullRegisteredDay_CreateMethodCalled(){
        // Given
        String currentDate = "12-04-2020";
        String comingTime = "8:00";
        DBReader dbReaderStub = Mockito.mock(DBReader.class);
        DBWriter dbWriterMock = Mockito.mock(DBWriter.class);

        Context contextStub = Mockito.mock(Context.class);

        sut = new TimeRegister(contextStub);

        sut.setDbReader(dbReaderStub);
        sut.setDbWriter(dbWriterMock);

        Mockito.doReturn(null).when(dbReaderStub).getRegisteredDay(currentDate);

        // When
        sut.saveComingTime(currentDate,comingTime);

        // Then
        Mockito.verify(dbWriterMock,Mockito.times(1)).createComingTime(currentDate,comingTime);
    }

}