package com.chilin.org.report.monthly;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.R;
import com.chilin.org.db.stempel.DBReader;
import com.chilin.org.model.Day;
import com.chilin.org.model.Month;
import com.chilin.org.util.DateTimeOperator;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class MonthlyReportActivity extends AppCompatActivity {

    private static final String TAG = "report-activity";
    private DBReader dbReader;
    private List<Day> daysForReport;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andresito);
        initializeValues();
        addInfoToReport();
        setUpButtonInToolbar();
        setTimeInMonth();
    }

    private void initializeValues() {
        Intent intentFromChooseMonth = getIntent();
        String monthForQuery = intentFromChooseMonth.getStringExtra(ChooseMonthActivity.MONTH_SELECTED);
        String selectedYear = intentFromChooseMonth.getStringExtra(ChooseMonthActivity.YEAR_SELECTED);
        daysForReport = getDbReader().getWorkedDaysOfMonth(monthForQuery, selectedYear);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addInfoToReport() {
        TableLayout report = findViewById(R.id.reportTable);
        report.addView(createReportHeader());

        for (Day dailyReport:daysForReport) {
            TableRow dailyInformation = createRow(dailyReport);
            report.addView(dailyInformation);
        }
        Log.i(TAG, "createReport executed successfully.");
    }

    private void setUpButtonInToolbar() {
        Toolbar reportActivityToolbar = findViewById(R.id.reportToolbar);
        setSupportActionBar(reportActivityToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTimeInMonth() {
        Month month = DateTimeOperator.getWorkingTimeInMonth(daysForReport);
        String workingTimeInMonthIst = month.getMonthlyWorkedTimeIST();
        String workingTimeInMonthSoll = month.getMonthlyWorkedTimeSOLL();

        TextView timeInMonth = findViewById(R.id.timeImMonthCalculated);
        TextView timeInMonthSOLL = findViewById(R.id.zeitSoll);
        timeInMonth.setText(workingTimeInMonthIst);
        timeInMonthSOLL.setText(workingTimeInMonthSoll);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private TableRow createReportHeader(){
        TextView datumHeader = createTextCell("Datum");
        TextView kommenHeader = createTextCell("Kommen");
        TextView pauseBeginnHeader = createTextCell("P.Beginn");
        TextView pauseEndeHeader = createTextCell("P.Ende");
        TextView gehenHeader = createTextCell("Gehen");
        TextView pauseHeader =createTextCell("Pause");

        TextView separatorText1 = createTextCell("||");
        TextView separatorText2 = createTextCell("||");
        TextView separatorText3 = createTextCell("||");
        TextView separatorText4 = createTextCell("||");
        TextView separatorText5 = createTextCell("||");
        TextView separatorText6 = createTextCell("||");
        TextView separatorText7 = createTextCell("||");

        TableRow tableHeader = new TableRow(this);
        tableHeader.addView(separatorText1);
        tableHeader.addView(datumHeader);
        tableHeader.addView(separatorText2);
        tableHeader.addView(kommenHeader);
        tableHeader.addView(separatorText3);
        tableHeader.addView(pauseBeginnHeader);
        tableHeader.addView(separatorText4);
        tableHeader.addView(pauseEndeHeader);
        tableHeader.addView(separatorText5);
        tableHeader.addView(gehenHeader);
        tableHeader.addView(separatorText6);
        tableHeader.addView(pauseHeader);
        tableHeader.addView(separatorText7);

        Log.i(TAG, "createRow executed successfully.");
        return tableHeader;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private TableRow createRow(Day dailyReport) {
        TextView workingDay = createTextCell(dailyReport.getDayRegistered());
        TextView comingTime = createTextCell(dailyReport.getComingTime());
        TextView pauseBeginnTime = createTextCell(dailyReport.getBeginnPause());
        TextView pauseEndeTime = createTextCell(dailyReport.getEndePause());
        TextView leavingTime = createTextCell(dailyReport.getLeavingTime());
        TextView pauseView = createTextCell(DateTimeOperator
                .createPause(dailyReport.getDayRegistered(), dailyReport.getBeginnPause()
                        ,dailyReport.getEndePause()));

        TextView separatorText1 = createTextCell("||");
        TextView separatorText2 = createTextCell("||");
        TextView separatorText3 = createTextCell("||");
        TextView separatorText4 = createTextCell("||");
        TextView separatorText5 = createTextCell("||");
        TextView separatorText6 = createTextCell("||");
        TextView separatorText7 = createTextCell("||");

        TableRow tableRow = new TableRow(this);
        tableRow.addView(separatorText1);
        tableRow.addView(workingDay);
        tableRow.addView(separatorText2);
        tableRow.addView(comingTime);
        tableRow.addView(separatorText3);
        tableRow.addView(pauseBeginnTime);
        tableRow.addView(separatorText4);
        tableRow.addView(pauseEndeTime);
        tableRow.addView(separatorText5);
        tableRow.addView(leavingTime);
        tableRow.addView(separatorText6);
        tableRow.addView(pauseView);
        tableRow.addView(separatorText7);

        Log.i(TAG, "createRow executed successfully.");
        return tableRow;
    }

    private TextView createTextCell(String text){
        if (StringUtils.isBlank(text)){
            text = "<->";
        }
        TextView textCell = new TextView(this);
        textCell.setText(text);
        textCell.setGravity(Gravity.CENTER);
        Log.i(TAG, "createTextCell executed successfully.");
        return textCell;
    }

    private DBReader getDbReader(){
        if (this.dbReader == null){
            this.dbReader = new DBReader(this);
        }
        return this.dbReader;
    }
}
