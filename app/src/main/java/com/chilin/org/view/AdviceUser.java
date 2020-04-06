package com.chilin.org.view;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class AdviceUser {

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

    public void showSorryWeekend(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Alter, es ist Wochenende! Gehe mal schlafen!").setPositiveButton("Ok", dialogSorryWeekend).show();
    }

    public void showSorryBackupNotPossible(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Sorry! Das Backup konnte nicht erstellt werden").setPositiveButton("Ok", dialogSorryWeekend).show();
    }

    public void showBackupWurdeErstellt(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Ein Backup wurde erstellt! Schau mal dein Drive Konto").setPositiveButton("Ok", dialogSorryWeekend).show();
    }

}
