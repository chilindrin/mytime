package com.chilin.org.report;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.chilin.org.MainActivity;
import com.chilin.org.R;
import com.chilin.org.db.DBReader;
import com.chilin.org.db.TimeRegister;
import com.chilin.org.model.Day;
import com.chilin.org.util.DateTimeOperationsProvider;
import com.chilin.org.util.TextProcessor;

import java.time.LocalDateTime;

public class DailyReportActivity extends AppCompatActivity {

    private DBReader dbReader;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        setUpButtonInToolbar();
        this.dbReader = new DBReader(this);
        setTimeInDailyReport();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTimeInDailyReport() {
        Intent intentFromMainActivity = getIntent();
        LocalDateTime currentDateFromMainDisplay = (LocalDateTime) intentFromMainActivity.getSerializableExtra(MainActivity.CURENT_DATE_OBJEKT);
        String friendlyFormatCurrentDate = DateTimeOperationsProvider.getFriendlyFormatCurrentDate(currentDateFromMainDisplay);
        Day registeredDay = this.dbReader.getRegisteredDay(friendlyFormatCurrentDate);

        TextView dailyCurrentDate = findViewById(R.id.dailyCurrentDate);
        dailyCurrentDate.setText(friendlyFormatCurrentDate);

        TextView dailyKommen = findViewById(R.id.dailyKommen);
        dailyKommen.setText(registeredDay.getComingTime());

        TextView dailyBeginnPause = findViewById(R.id.dailyBeginnPause);
        dailyBeginnPause.setText(registeredDay.getBeginnPause());

        TextView dailyEndePause = findViewById(R.id.dailyEndePause);
        dailyEndePause.setText(registeredDay.getEndePause());

        TextView dailyGehen = findViewById(R.id.dailyGehen);
        dailyGehen.setText(registeredDay.getLeavingTime());
    }

    private void setUpButtonInToolbar() {
        Toolbar dailyReportToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(dailyReportToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
