package com.chilin.org.report;

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
import com.chilin.org.db.TimeRegister;
import com.chilin.org.util.DateTimeOperationsProvider;
import com.chilin.org.util.DayTableOrder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ReportCreatorActivity extends AppCompatActivity {

    private static final String TAG = "report-activity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andresito);
        addInfoToReport();
        setUpButtonInToolbar();
    }

    private void setUpButtonInToolbar() {
        Toolbar reportActivityToolbar = findViewById(R.id.reportToolbar);
        setSupportActionBar(reportActivityToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addInfoToReport() {
        TableLayout report = findViewById(R.id.reportTable);
        report.addView(createReportHeader());

        TimeRegister timeRegister = new TimeRegister(this);
        List<String[]> dailyResults = timeRegister.getAllDataInDB();
        for (String[] dailyReport:dailyResults) {
            TableRow dailyInformation = createRow(dailyReport);
            report.addView(dailyInformation);
        }
        Log.i(TAG, "createReport executed successfully.");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private TableRow createReportHeader(){
        TextView datumHeader = createTextCell("Datum");
        TextView kommenHeader = createTextCell("Kommen");
        TextView gehenHeader = createTextCell("Gehen");
        TextView pauseHeader =createTextCell("Pause");

        TextView separatorText1 = createTextCell("||");
        TextView separatorText2 = createTextCell("||");
        TextView separatorText3 = createTextCell("||");
        TextView separatorText4 = createTextCell("||");
        TextView separatorText5 = createTextCell("||");

        TableRow tableHeader = new TableRow(this);
        tableHeader.addView(separatorText1);
        tableHeader.addView(datumHeader);
        tableHeader.addView(separatorText2);
        tableHeader.addView(kommenHeader);
        tableHeader.addView(separatorText3);
        tableHeader.addView(gehenHeader);
        tableHeader.addView(separatorText4);
        tableHeader.addView(pauseHeader);
        tableHeader.addView(separatorText5);

        Log.i(TAG, "createRow executed successfully.");
        return tableHeader;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private TableRow createRow(String[] dailyReport) {
        TextView workingDay = createTextCell(dailyReport[DayTableOrder.CURRENT_DAY_POSITION]);
        TextView comingTime = createTextCell(dailyReport[DayTableOrder.COMMING_POSITION]);
        TextView leavingTime = createTextCell(dailyReport[DayTableOrder.LEAVING_POSITION]);
        TextView pauseView = createTextCell(DateTimeOperationsProvider.createPause(
                dailyReport[DayTableOrder.CURRENT_DAY_POSITION],
                dailyReport[DayTableOrder.BEGINN_PAUSE_POSITION],
                dailyReport[DayTableOrder.ENDE_PAUSE_POSITION]));

        TextView separatorText1 = createTextCell("||");
        TextView separatorText2 = createTextCell("||");
        TextView separatorText3 = createTextCell("||");
        TextView separatorText4 = createTextCell("||");
        TextView separatorText5 = createTextCell("||");

        TableRow tableRow = new TableRow(this);
        tableRow.addView(separatorText1);
        tableRow.addView(workingDay);
        tableRow.addView(separatorText2);
        tableRow.addView(comingTime);
        tableRow.addView(separatorText3);
        tableRow.addView(leavingTime);
        tableRow.addView(separatorText4);
        tableRow.addView(pauseView);
        tableRow.addView(separatorText5);

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
}
