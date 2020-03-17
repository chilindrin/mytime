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
        TableLayout tableLayout = createTable();
        setContentView(tableLayout);
    }

    private TableLayout createTable() {
        TimeRegister timeRegister = new TimeRegister();
        TableLayout tableLayout = new TableLayout(this);
        List<String[]> dbResults = timeRegister.getAllDataInDB(tableLayout.getContext());
        for (String[] dbResult:dbResults) {
            TableRow tableRow = createRow(dbResult);
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }

    private TableRow createRow(String[] dbResult) {
        TextView dateText = createTextCell(dbResult[0]);
        TextView comingText = createTextCell(dbResult[1]);
        TextView leavingText = createTextCell(dbResult[2]);
        TextView separatorText1 = createTextCell("||");
        TextView separatorText2 = createTextCell("||");
        TextView separatorText3 = createTextCell("||");
        TextView separatorText4 = createTextCell("||");

        TableRow tableRow = new TableRow(this);
        tableRow.addView(separatorText1);
        tableRow.addView(dateText);
        tableRow.addView(separatorText2);
        tableRow.addView(comingText);
        tableRow.addView(separatorText3);
        tableRow.addView(leavingText);
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
