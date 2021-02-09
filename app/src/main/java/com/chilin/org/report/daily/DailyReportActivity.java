package com.chilin.org.report.daily;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.R;
import com.chilin.org.model.Day;
import com.chilin.org.util.DateTimeOperator;

import org.apache.commons.lang3.StringUtils;

public class DailyReportActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        setUpButtonInToolbar();
        setTimesInDailyReport();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTimesInDailyReport() {
        Intent intentFromChooseDateActivity = getIntent();
        Bundle extrasFromChooseDateActivity = intentFromChooseDateActivity.getExtras();
        Day registeredDay = (Day) extrasFromChooseDateActivity.getSerializable(ChooseDateActivity.REGISTERED_DAY);
        String dayRegistered = registeredDay.getDayRegistered();
        String comingTime = registeredDay.getComingTime();
        String beginnPause = registeredDay.getBeginnPause();
        String endePause = registeredDay.getEndePause();
        String leavingTime = registeredDay.getLeavingTime();

        TextView dailyCurrentDate = findViewById(R.id.dailyReportCurrentDay);
        dailyCurrentDate.setText(StringUtils.isNotBlank(dayRegistered)
                ? dayRegistered : "<->");

        TextView dailyKommen = findViewById(R.id.dailyReportComingTime);
        dailyKommen.setText(StringUtils.isNotBlank(comingTime)
                ? comingTime : "<->");

        TextView dailyBeginnPause = findViewById(R.id.dailyReportBeginnPause);
        dailyBeginnPause.setText(StringUtils.isNotBlank(beginnPause)
                        ? beginnPause : "<->");

        TextView dailyEndePause = findViewById(R.id.dailyReportEndePause);
        dailyEndePause.setText(StringUtils.isNotBlank(endePause)
                        ? endePause : "<->");

        TextView dailyGehen = findViewById(R.id.dailyReportLeavingTime);
        dailyGehen.setText(StringUtils.isNotBlank(leavingTime)
                ? leavingTime : "<->");

        TextView pauseDauerView = findViewById(R.id.dailyReportPauseDauer);
        pauseDauerView.setText(DateTimeOperator.createPause(dayRegistered,
                beginnPause, endePause));

        TextView workingTimeDay = findViewById(R.id.dailyReportArbeitTag);
        workingTimeDay.setText(DateTimeOperator.getWorkedTimeInDay(registeredDay));
    }

    private void setUpButtonInToolbar() {
        Toolbar dailyReportToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(dailyReportToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
