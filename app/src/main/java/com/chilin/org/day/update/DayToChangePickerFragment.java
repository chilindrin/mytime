package com.chilin.org.day.update;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.chilin.org.util.TextProcessor;

import java.util.Calendar;

public class DayToChangePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView dayToChangeView;

    public DayToChangePickerFragment(TextView dayToChangeView){
        this.dayToChangeView = dayToChangeView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // dd-MM-yyyy
        String formattedChosenDate = TextProcessor.formatChosenDate(day,month+1,year);
        this.dayToChangeView.setText(formattedChosenDate);
    }
}