package com.chilin.org.day.update;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.chilin.org.R;
import com.chilin.org.advice.AdviceUser;
import com.chilin.org.db.stempel.DBReader;
import com.chilin.org.exception.MyTimeException;
import com.chilin.org.model.Day;
import com.chilin.org.util.DateTimeOperator;

import java.time.LocalDateTime;

import static com.chilin.org.MainActivity.CURENT_DATE_OBJEKT;

public class ChooseDayToChange extends AppCompatActivity {

    public final static String DAY_TO_CHANGE = "DAY.TO.CHANGE";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_day_to_change);
        setUpButtonInToolbar();
        setDateThatCouldBeChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDateThatCouldBeChanged() {
        Intent intentFromMainActivity = getIntent();
        LocalDateTime currentDateFromMainDisplay = (LocalDateTime) intentFromMainActivity
                .getSerializableExtra(CURENT_DATE_OBJEKT);
        String friendlyFormatCurrentDate = DateTimeOperator.getFriendlyFormatCurrentDate(currentDateFromMainDisplay);
        TextView dayToChange = findViewById(R.id.selectedDayToChange);
        dayToChange.setText(friendlyFormatCurrentDate);
    }

    private void setUpButtonInToolbar() {
        Toolbar changeDayToolbar = findViewById(R.id.changeDayToolbar);
        setSupportActionBar(changeDayToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void showDayToChangePicker(View view){
        TextView dayToChange = findViewById(R.id.selectedDayToChange);
        DialogFragment datePickerFragment = new DayToChangePickerFragment(dayToChange);
        datePickerFragment.show(getSupportFragmentManager(), "dateChangePicker");
    }

    public void dayToChangeChosen(View view){
        TextView dayToChangeView = findViewById(R.id.selectedDayToChange);
        String chosenDayToChange = (String) dayToChangeView.getText();

        DBReader dbReader = new DBReader(this);
        Day registeredDayFromDBToChange = dbReader.getRegisteredDay(chosenDayToChange);

        if (registeredDayFromDBToChange == null){
            new AdviceUser().showNoDataForThisDateStored(this);
        } else {
            startActivityThatChangesSelectedDay(registeredDayFromDBToChange);
        }
    }

    private void startActivityThatChangesSelectedDay(Day registeredDayFromDBToChange) {
        Bundle infoFromDayToChange = new Bundle();
        infoFromDayToChange.putSerializable(DAY_TO_CHANGE,registeredDayFromDBToChange);
        Intent intentDayToChange = new Intent(this, DayEditorActivity.class);
        intentDayToChange.putExtras(infoFromDayToChange);
        try {
            startActivity(intentDayToChange);
        } catch (MyTimeException e){
            new AdviceUser().showProblemCalculatingWorkingTime(this);
        }
    }
}
