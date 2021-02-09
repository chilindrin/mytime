package com.chilin.org.day.delete;

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

public class ChooseDayToDelete extends AppCompatActivity {

    public final static String DAY_TO_DELETE = "DAY.TO.DELETE";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_day_to_delete);
        setUpButtonInToolbar();
        setDateThatCouldBeDeleted();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDateThatCouldBeDeleted() {
        Intent intentFromMainActivity = getIntent();
        LocalDateTime currentDateFromMainDisplay = (LocalDateTime) intentFromMainActivity
                .getSerializableExtra(CURENT_DATE_OBJEKT);
        String friendlyFormatCurrentDate = DateTimeOperator.getFriendlyFormatCurrentDate(currentDateFromMainDisplay);
        TextView dayToDeleteView = findViewById(R.id.selectedDayToDelete);
        dayToDeleteView.setText(friendlyFormatCurrentDate);
    }

    private void setUpButtonInToolbar() {
        Toolbar deleteDayToolbar = findViewById(R.id.deleteDayToolbar);
        setSupportActionBar(deleteDayToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void showDatePickerToChooseDayToDelete(View view){
        TextView dayToDeleteView = findViewById(R.id.selectedDayToDelete);
        DialogFragment datePickerFragment = new DayToDeletePickerFragment(dayToDeleteView);
        datePickerFragment.show(getSupportFragmentManager(), "dateDeletePicker");
    }

    public void getDateFromDBAndPrepareActivity(View view){
        TextView dayToDeleteView = findViewById(R.id.selectedDayToDelete);
        String chosenDayToDelete = (String) dayToDeleteView.getText();

        DBReader dbReader = new DBReader(this);
        Day registeredDayFromDBToDelete = dbReader.getRegisteredDay(chosenDayToDelete);

        if (registeredDayFromDBToDelete == null){
            new AdviceUser().showNoDataForThisDateStored(this);
        } else {
            startActivityThatDeletesSelectedDay(registeredDayFromDBToDelete);
        }
    }

    private void startActivityThatDeletesSelectedDay(Day dayFromDBToDelete) {
        Bundle infoFromDayToDelete = new Bundle();
        infoFromDayToDelete.putSerializable(DAY_TO_DELETE,dayFromDBToDelete);
        Intent intentDayToDelete = new Intent(this, DayEraserActivity.class);
        intentDayToDelete.putExtras(infoFromDayToDelete);
        try {
            startActivity(intentDayToDelete);
        } catch (MyTimeException e){
            new AdviceUser().showProblemCalculatingWorkingTime(this);
        }
    }
}
