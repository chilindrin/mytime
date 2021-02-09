package com.chilin.org.db.abruf;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.chilin.org.model.Abruf;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class WriterTest {

    private Writer sut;
    private Reader abrufReader;
    private Context applicationContext;

    @Before
    public void setUp(){
        applicationContext = ApplicationProvider.getApplicationContext();
        sut = new Writer(applicationContext);
        abrufReader = new Reader(applicationContext);
    }

    @After
    public void tearDown(){
        applicationContext = null;
        sut = null;
        abrufReader = null;
    }

    @Test
    public void createAbruf_AbrufAlsValueObject_AbrufCreatedInDB(){
        // Given
        final Abruf abruf = new Abruf();
        abruf.setName("20-0865-SEP53-LION");
        abruf.setAnfang("11.2020");
        abruf.setEnde("04.2021");
        abruf.setStunden("1600");

        // When
        sut.createAbruf(abruf);

        List<Abruf> allAbrufeFromDB = abrufReader.getAllAbrufeFromDB();
        boolean abrufWasCreated = allAbrufeFromDB.stream().anyMatch(abrufFromDB -> abrufFromDB.equals(abruf));

        // Then
        Assert.assertTrue(abrufWasCreated);

        sut.deleteAbruf(abruf.getName());
    }

}
