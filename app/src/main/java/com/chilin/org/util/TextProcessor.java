package com.chilin.org.util;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.chilin.org.constants.Operation;
import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class TextProcessor {

    private static final String TAG = "text-processor";
    private static final String EMPTY_RESULT = "<->";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertDBContentToString(List<Day> allDataInDB){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<allDataInDB.size();i++) {
            Day registeredDay = allDataInDB.get(i);

            stringBuilder.append(StringUtils.isBlank(registeredDay.getDayRegistered()) ? EMPTY_RESULT : registeredDay.getDayRegistered());
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(registeredDay.getComingTime()) ? EMPTY_RESULT : registeredDay.getComingTime());
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(registeredDay.getBeginnPause()) ? EMPTY_RESULT : registeredDay.getBeginnPause());
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(registeredDay.getEndePause()) ? EMPTY_RESULT : registeredDay.getEndePause());
            stringBuilder.append(',');
            stringBuilder.append(StringUtils.isBlank(registeredDay.getLeavingTime()) ? EMPTY_RESULT : registeredDay.getLeavingTime());
            stringBuilder.append(',');
            String pause = DateTimeOperator.createPause(registeredDay.getDayRegistered(), registeredDay.getBeginnPause(),registeredDay.getEndePause());
            stringBuilder.append(pause);
            stringBuilder.append(" min");
            if (i != allDataInDB.size() - 1){
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        Log.i(TAG,"convertDBContentToString Successfull");
        return stringBuilder.toString();
    }

    public static boolean isNotPresent(String input){
        return StringUtils.isBlank(input) || input.equals(EMPTY_RESULT);
    }

    public static boolean isPresent(String input){
        return StringUtils.isNotBlank(input) && !input.equals(EMPTY_RESULT);
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

    public static String getTextForOperation(Operation operation){
        switch (operation){
            case COMING:
                return "Guten morgen!";
            case BEGINN_PAUSE:
                return "Pause ab wann?";
            case ENDE_PAUSE:
                return "Es geht weiter!";
            case LEAVING:
                return "Es reicht mir!";
            default:
                throw new MyTimeException("The operation you are trying to do does not exist");
        }
    }

    public static String getTime(int hours,int minutes){
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String getTwoDigitsNumber(int input){
        return String.format("%02d", input);
    }

    public static String formatChosenDate(int day, int month, int year){
        // "dd-MM-yyyy"
        return String.format("%02d-%02d-%04d", day, month,year);
    }

}
