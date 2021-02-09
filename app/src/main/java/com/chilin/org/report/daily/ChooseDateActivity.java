package com.chilin.org.report.daily;

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

public class ChooseDateActivity extends AppCompatActivity {

    public final static String REGISTERED_DAY = "REGISTERED.DAY";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        setUpButtonInToolbar();
        setOptionDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setOptionDate() {
        Intent intentFromMainActivity = getIntent();
        LocalDateTime currentDateFromMainDisplay = (LocalDateTime) intentFromMainActivity
                .getSerializableExtra(CURENT_DATE_OBJEKT);
        String friendlyFormatCurrentDate = DateTimeOperator.getFriendlyFormatCurrentDate(currentDateFromMainDisplay);
        TextView dateForReport = findViewById(R.id.selectDateField);
        dateForReport.setText(friendlyFormatCurrentDate);
    }

    private void setUpButtonInToolbar() {
        Toolbar reportActivityToolbar = findViewById(R.id.chooseDateToolbar);
        setSupportActionBar(reportActivityToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void showDatePicker(View view){
        TextView dateForReport = findViewById(R.id.selectDateField);
        DialogFragment newFragment = new DatePickerFragment(dateForReport);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void dateForReportChosen(View view){
        TextView dateForReport = findViewById(R.id.selectDateField);
        String chosenDateForDailyReport = (String) dateForReport.getText();

        DBReader dbReader = new DBReader(this);
        Day registeredDayFromDBForDailyReport = dbReader.getRegisteredDay(chosenDateForDailyReport);

        if (registeredDayFromDBForDailyReport == null){
            new AdviceUser().showNoDataForThisDateStored(this);
        } else {
            showValuesForChosenDay(registeredDayFromDBForDailyReport);
        }
    }

    private void showValuesForChosenDay(Day registeredDayFromDBForDailyReport) {
        Bundle infoForDailyReport = new Bundle();
        infoForDailyReport.putSerializable(REGISTERED_DAY,registeredDayFromDBForDailyReport);
        Intent intentForDailyReport = new Intent(this, DailyReportActivity.class);
        intentForDailyReport.putExtras(infoForDailyReport);
        try {
            startActivity(intentForDailyReport);
        } catch (MyTimeException e){
            new AdviceUser().showProblemCalculatingWorkingTime(this);
        }
    }
}
