package com.chilin.org.advice;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class AdviceUser {

    DialogInterface.OnClickListener smallDialogForUser = new DialogInterface.OnClickListener() {

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
        builder.setMessage("Alter, es ist Wochenende! Gehe mal schlafen!").setPositiveButton("Ok", smallDialogForUser).show();
    }

    public void showSorryBackupNotPossible(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Sorry! Das Backup konnte nicht erstellt werden").setPositiveButton("Ok", smallDialogForUser).show();
    }

    public void showBeginnPauseAfterLeavingTime(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton("Ok", smallDialogForUser).show();
    }

    public void showFeierabendBerechnung(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton("Ok", smallDialogForUser).show();
    }

    public void showNoDataForThisDateStored(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Es gibt kein Data für das ausgewählte Datum").setPositiveButton("Ok", smallDialogForUser).show();
    }

    public void showProblemCalculatingWorkingTime(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Überprüfe deine Zeiten, etwas passt nicht").setPositiveButton("Ok", smallDialogForUser).show();
    }

}
