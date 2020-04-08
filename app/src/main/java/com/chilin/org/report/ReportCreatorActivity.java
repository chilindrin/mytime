package com.chilin.org.report;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.R;
import com.chilin.org.db.TimeRegister;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ReportCreatorActivity extends AppCompatActivity {

    private static final String TAG = "report-activity";

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

    private void addInfoToReport() {
        String[] tableHeader = {"Datum","Kommen","Gehen","P.Start","P.Ende"};
        TableRow tableHeaderView = createRow(tableHeader);
        TableLayout report = findViewById(R.id.reportTable);
        report.addView(tableHeaderView);
        TimeRegister timeRegister = new TimeRegister(this);
        List<String[]> dailyResults = timeRegister.getAllDataInDB();
        for (String[] dailyReport:dailyResults) {
            TableRow dailyInformation = createRow(dailyReport);
            report.addView(dailyInformation);
        }
        Log.i(TAG, "createReport executed successfully.");
    }

    private TableRow createRow(String[] dailyReport) {
        TextView workingDay = createTextCell(dailyReport[0]);
        TextView comingTime = createTextCell(dailyReport[1]);
        TextView leavingTime = createTextCell(dailyReport[2]);
        TextView beginnPauseTime = createTextCell(dailyReport[3]);
        TextView endePauseTime = createTextCell(dailyReport[4]);

        TextView separatorText1 = createTextCell("||");
        TextView separatorText2 = createTextCell("||");
        TextView separatorText3 = createTextCell("||");
        TextView separatorText4 = createTextCell("||");
        TextView separatorText5 = createTextCell("||");
        TextView separatorText6 = createTextCell("||");

        TableRow tableRow = new TableRow(this);
        tableRow.addView(separatorText1);
        tableRow.addView(workingDay);
        tableRow.addView(separatorText2);
        tableRow.addView(comingTime);
        tableRow.addView(separatorText3);
        tableRow.addView(leavingTime);
        tableRow.addView(separatorText4);
        tableRow.addView(beginnPauseTime);
        tableRow.addView(separatorText5);
        tableRow.addView(endePauseTime);
        tableRow.addView(separatorText6);

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
