package com.chilin.org;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.backup.BackupCreatorActivity;
import com.chilin.org.constants.Operation;
import com.chilin.org.db.TimeRegister;
import com.chilin.org.report.DailyReportActivity;
import com.chilin.org.report.ReportCreatorActivity;
import com.chilin.org.util.DateTimeOperationsProvider;
import com.chilin.org.view.AdviceUser;
import com.chilin.org.stempeln.InsertTime;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    public static final String OPERATION = "OPERATION";
    public static final String CURENT_DATE_OBJEKT = "CURRENT_DATE_OBJEKT";

    private TimeRegister timeRegister;
    private LocalDateTime currentDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.timeRegister = new TimeRegister(this);
        setCurrentDateOnDisplay();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.createBackup:
                showCreateBackupActivity();
                return true;
            case R.id.showShortReport:
                showShortReport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showShortReport() {
        Intent intentForDailyReport = new Intent(this, DailyReportActivity.class);
        startActivity(intentForDailyReport);
    }

    private void showCreateBackupActivity() {
        Intent intent = new Intent(this, BackupCreatorActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCurrentDateOnDisplay() {
        this.currentDate = DateTimeOperationsProvider.getCurrentDate();

        TextView currentDateTextView = findViewById(R.id.currentDateTextView);
        currentDateTextView.setText(DateTimeOperationsProvider.getFriendlyFormatCurrentDate(this.currentDate));
    }

    public void createReport(View view){
        Intent intent = new Intent(this, ReportCreatorActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveComingTime(View view){
        if (DateTimeOperationsProvider.isWeekend(this.currentDate)){
            new AdviceUser().showSorryWeekend(view.getContext());
        } else {
            Intent intent = new Intent(this, InsertTime.class);
            intent.putExtra(CURENT_DATE_OBJEKT,this.currentDate);
            intent.putExtra(OPERATION, Operation.COMING);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void beginnPause(View view){
        if (DateTimeOperationsProvider.isWeekend(this.currentDate)){
            new AdviceUser().showSorryWeekend(view.getContext());
        } else {
            Intent intent = new Intent(this, InsertTime.class);
            intent.putExtra(CURENT_DATE_OBJEKT,this.currentDate);
            intent.putExtra(OPERATION, Operation.BEGINN_PAUSE);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void endePause(View view){
        if (DateTimeOperationsProvider.isWeekend(this.currentDate)){
            new AdviceUser().showSorryWeekend(view.getContext());
        } else {
            Intent intent = new Intent(this, InsertTime.class);
            intent.putExtra(CURENT_DATE_OBJEKT,this.currentDate);
            intent.putExtra(OPERATION, Operation.ENDE_PAUSE);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveLeavingTime(View view) {
        if (DateTimeOperationsProvider.isWeekend(this.currentDate)){
            new AdviceUser().showSorryWeekend(view.getContext());
        } else {
            Intent intent = new Intent(this, InsertTime.class);
            intent.putExtra(CURENT_DATE_OBJEKT,this.currentDate);
            intent.putExtra(OPERATION, Operation.LEAVING);
            startActivity(intent);
        }
    }

}
