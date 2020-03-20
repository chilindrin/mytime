package com.chilin.org;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chilin.org.db.TimeRegister;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TableLayout report = createReport();
        setContentView(report);
    }

    private TableLayout createReport() {
        TableLayout report = new TableLayout(this);
        TimeRegister timeRegister = new TimeRegister();
        List<String[]> dailyResults = timeRegister.getAllDataInDB(report.getContext());
        for (String[] dailyReport:dailyResults) {
            TableRow dailyInformation = createRow(dailyReport);
            report.addView(dailyInformation);
        }
        return report;
    }

    private TableRow createRow(String[] dbResult) {
        TextView workingDay = createTextCell(dbResult[0]);
        TextView comingTime = createTextCell(dbResult[1]);
        TextView leavingTime = createTextCell(dbResult[2]);
        TextView separatorText1 = createTextCell("||");
        TextView separatorText2 = createTextCell("||");
        TextView separatorText3 = createTextCell("||");
        TextView separatorText4 = createTextCell("||");

        TableRow tableRow = new TableRow(this);
        tableRow.addView(separatorText1);
        tableRow.addView(workingDay);
        tableRow.addView(separatorText2);
        tableRow.addView(comingTime);
        tableRow.addView(separatorText3);
        tableRow.addView(leavingTime);
        tableRow.addView(separatorText4);
        return tableRow;
    }

    private TextView createTextCell(String text){
        if (StringUtils.isBlank(text)){
            text = "<not yet inserted>";
        }
        TextView textCell = new TextView(this);
        textCell.setText(text);
        textCell.setGravity(Gravity.CENTER);
        return textCell;
    }
}
