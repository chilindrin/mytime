package com.chilin.org.feierabend;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.chilin.org.db.stempel.DBReader;
import com.chilin.org.model.Day;
import com.chilin.org.util.DateTimeOperator;

public class FeierabendRechner {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateFeierabend(String currentDate, Context context){
        String messageHeader = "Feierabend möglich ab: ";
        DBReader dbReader = new DBReader(context);
        Day registeredDay = dbReader.getRegisteredDay(currentDate);
        return messageHeader + DateTimeOperator.calculateFeierabend(registeredDay);
    }

}
