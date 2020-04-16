package com.chilin.org.util;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.chilin.org.constants.Operation;
import com.chilin.org.exception.MyTimeException;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TextProcessor {

    private static final String TAG = "text-processor";
    private static final String EMPTY_RESULT = "<->";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertDBContentToString(List<String[]> allDataInDB){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<allDataInDB.size();i++) {
            String[] content = allDataInDB.get(i);

            stringBuilder.append(StringUtils.isBlank(
                    content[DayTableOrder.CURRENT_DAY_POSITION])
                    ? EMPTY_RESULT : content[DayTableOrder.CURRENT_DAY_POSITION]);
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(
                    content[DayTableOrder.COMMING_POSITION])
                    ? EMPTY_RESULT : content[DayTableOrder.COMMING_POSITION]);
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(
                    content[DayTableOrder.LEAVING_POSITION])
                    ? EMPTY_RESULT : content[DayTableOrder.LEAVING_POSITION]);
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(
                    content[DayTableOrder.BEGINN_PAUSE_POSITION])
                    ? EMPTY_RESULT : content[DayTableOrder.BEGINN_PAUSE_POSITION]);
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(
                    content[DayTableOrder.ENDE_PAUSE_POSITION])
                    ? EMPTY_RESULT : content[DayTableOrder.ENDE_PAUSE_POSITION]);
            stringBuilder.append(',');

            String pause = DateTimeOperationsProvider.createPause(
                    content[DayTableOrder.CURRENT_DAY_POSITION],
                    content[DayTableOrder.BEGINN_PAUSE_POSITION],
                    content[DayTableOrder.ENDE_PAUSE_POSITION]);

            stringBuilder.append(pause);
            stringBuilder.append(" min");
            if (i != allDataInDB.size() - 1){
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        Log.i(TAG,"convertDBContentToString Successfull");
        return stringBuilder.toString();
    }

    public static byte[] convertStringToByteArray(String input){
        return input.getBytes(Charset.forName("UTF-8"));
    }

    public static String convertByteArrayToString(byte[] input){
        return new String(input, Charset.forName("UTF-8"));
    }

    public static String createFileName(){
        String randomUUID = UUID.randomUUID().toString();
        return "mytime-"+randomUUID+".txt";
    }

    public static String convertList(){
        List<String[]> dbContent = new ArrayList<>();
        String[] primero = {"30-03-2020","12:13","15:13"};
        String[] segundo = {"30-04-2020","12:13","15:13"};
        String[] tercero = {"30-05-2020","12:13","15:13"};
        dbContent.add(primero);
        dbContent.add(segundo);
        dbContent.add(tercero);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<dbContent.size();i++) {
            String[] content = dbContent.get(i);

            stringBuilder.append(content[0]);
            stringBuilder.append(',');
            stringBuilder.append(content[1]);
            stringBuilder.append(',');
            stringBuilder.append(content[2]);
            if (i != dbContent.size() - 1){
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        return stringBuilder.toString();
    }

    public static String getText(Operation operation){
        switch (operation){
            case BEGINN_PAUSE:
                return "Pause ab wann?";
            default:
                throw new MyTimeException("The operation you are trying to do does not exist");
        }
    }

    public static String getTime(String hours,String minutes){
        return hours + ":" + minutes;
    }

}
