package com.chilin.org.util;

import android.util.Log;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TextProcessor {

    private static final String TAG = "text-processor";

    public static String convertDBContentToString(List<String[]> allDataInDB){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<allDataInDB.size();i++) {
            String[] content = allDataInDB.get(i);
            stringBuilder.append(content[0]);
            stringBuilder.append(',');
            stringBuilder.append(content[1]);
            stringBuilder.append(',');
            stringBuilder.append(content[2]);
            stringBuilder.append(',');
            stringBuilder.append(content[3]);
            stringBuilder.append(',');
            stringBuilder.append(content[4]);
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



}
