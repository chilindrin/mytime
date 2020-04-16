package com.chilin.org.db;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.games.multiplayer.realtime.Room;

import org.junit.Test;

import java.util.List;

public class DBReaderAndresTest {

    @Test
    public void testPrueba(){
        Context applicationContext = ApplicationProvider.getApplicationContext();

        DBReader dbReader = new DBReader(applicationContext);
        List<String[]> allDataInDB = dbReader.getAllDataInDB();
    }

}
