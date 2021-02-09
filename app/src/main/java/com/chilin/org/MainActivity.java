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

import com.chilin.org.advice.AdviceUser;
import com.chilin.org.backup.BackupCreatorActivity;
import com.chilin.org.constants.Operation;
import com.chilin.org.abruf.AbrufErstellerActivity;
import com.chilin.org.day.delete.ChooseDayToDelete;
import com.chilin.org.day.update.ChooseDayToChange;
import com.chilin.org.feierabend.FeierabendRechner;
import com.chilin.org.report.daily.ChooseDateActivity;
import com.chilin.org.report.monthly.ChooseMonthActivity;
import com.chilin.org.stempeln.InsertTime;
import com.chilin.org.util.DateTimeOperator;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    public static final String OPERATION = "OPERATION";
    public static final String CURENT_DATE_OBJEKT = "CURRENT_DATE_OBJEKT";

    private LocalDateTime currentDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCurrentDateOnDisplay();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.createBackup:
                showCreateBackupActivity();
                return true;
            case R.id.showDailyReport:
                showChooseDateForReportActivity();
                return true;
            case R.id.showMonthlyReport:
                showChooseMonthForReportActivity();
                return true;
            case R.id.editDay:
                showChooseDayToChange();
                return true;
            case R.id.deleteDay:
                showChooseDayToDelete();
                return true;
            case R.id.feierabend:
                calculateFeierabendTime();
                return true;
            case R.id.speichereAbruf:
                speichereAbruf();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void speichereAbruf() {
        Intent speichereAbrufIntent = new Intent(this, AbrufErstellerActivity.class);
        speichereAbrufIntent.putExtra(CURENT_DATE_OBJEKT, this.currentDate);
        startActivity(speichereAbrufIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void calculateFeierabendTime() {
        FeierabendRechner feierabendRechner = new FeierabendRechner();
        String feierabendMessage = feierabendRechner
                .calculateFeierabend(DateTimeOperator
                        .getFriendlyFormatCurrentDate(this.currentDate), this);
        new AdviceUser().showFeierabendBerechnung(this,feierabendMessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showChooseDayToDelete() {
        Intent deleteDayIntent = new Intent(this, ChooseDayToDelete.class);
        deleteDayIntent.putExtra(CURENT_DATE_OBJEKT, this.currentDate);
        startActivity(deleteDayIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showChooseDayToChange() {
        Intent changeDayIntent = new Intent(this, ChooseDayToChange.class);
        changeDayIntent.putExtra(CURENT_DATE_OBJEKT, this.currentDate);
        startActivity(changeDayIntent);
    }

    private void showChooseMonthForReportActivity() {
        Intent intentForMonthlyReport = new Intent(this, ChooseMonthActivity.class);
        startActivity(intentForMonthlyReport);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showChooseDateForReportActivity() {
        Intent intentForDailyReport = new Intent(this, ChooseDateActivity.class);
        intentForDailyReport.putExtra(CURENT_DATE_OBJEKT, this.currentDate);
        startActivity(intentForDailyReport);
    }

    private void showCreateBackupActivity() {
        Intent intent = new Intent(this, BackupCreatorActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCurrentDateOnDisplay() {
        this.currentDate = DateTimeOperator.getCurrentDate();

        TextView currentDateTextView = findViewById(R.id.currentDateTextView);
        currentDateTextView.setText(DateTimeOperator.getFriendlyFormatCurrentDate(this.currentDate));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveComingTime(View view){
        if (DateTimeOperator.isWeekend(this.currentDate)){
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
        if (DateTimeOperator.isWeekend(this.currentDate)){
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
        if (DateTimeOperator.isWeekend(this.currentDate)){
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
        if (DateTimeOperator.isWeekend(this.currentDate)){
            new AdviceUser().showSorryWeekend(view.getContext());
        } else {
            Intent intent = new Intent(this, InsertTime.class);
            intent.putExtra(CURENT_DATE_OBJEKT,this.currentDate);
            intent.putExtra(OPERATION, Operation.LEAVING);
            startActivity(intent);
        }
    }

}
