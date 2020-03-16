package com.chilin.org;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.chilin.org.db.TimeDBDealer;
import com.chilin.org.db.TimeRegister;

import java.util.List;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeRegister timeRegister = new TimeRegister();

        TableLayout tableLayout = new TableLayout(this);
        List<String[]> dbResults = timeRegister.getAllDataInDB(tableLayout.getContext());
        for (int i = 0; i < 10; i++){
            TableRow tableRow = new TableRow(this);
            Button button = new Button(this);
            button.setText("1");
            tableRow.addView(button);

            button = new Button(this);
            button.setText("2");
            tableRow.addView(button);

            button = new Button(this);
            button.setText("3");
            tableRow.addView(button);

            tableLayout.addView(tableRow);
        }
        setContentView(tableLayout);
    }
}
