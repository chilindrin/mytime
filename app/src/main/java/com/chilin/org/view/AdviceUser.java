package com.chilin.org.view;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.chilin.org.db.TimeRegister;

public class AdviceUser {

    private TimeRegister timeRegister;
    private Context context;

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:


                    //timeRegister.deleteDB(context);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialogSorryWeekend = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    break;
            }
        }
    };

    public void showAdvice(Context context, TimeRegister timeRegister){
        this.context = context;
        this.timeRegister = timeRegister;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void showSorryWeekend(Context context){
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Alter, es ist Wochenende! Gehe mal schlafen!").setPositiveButton("Ok", dialogSorryWeekend).show();
    }

}
