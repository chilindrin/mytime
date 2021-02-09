package com.chilin.org.report.monthly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.R;
import com.chilin.org.util.DateTimeOperator;

public class ChooseMonthActivity extends AppCompatActivity {

    public static final String MONTH_SELECTED = "Month.selected";
    public static final String YEAR_SELECTED = "Year.selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_month);
        setUpButtonInToolbar();
        setMonthsInSpinner();
        setYearInSpinner();
    }

    private void setYearInSpinner() {
        Spinner yearSpinner = findViewById(R.id.yearSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choose_year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
    }

    private void setMonthsInSpinner() {
        Spinner monthSpinner = findViewById(R.id.monthSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choose_month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
    }

    private void setUpButtonInToolbar() {
        Toolbar monthReportToolBar = findViewById(R.id.chooseMonthToolbar);
        setSupportActionBar(monthReportToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void generateMonthReport(View view){
        Spinner monthSpinner = findViewById(R.id.monthSpinner);
        String selectedMonthForQuery = DateTimeOperator.convertMonth(monthSpinner.getSelectedItem().toString());
        Spinner yearSpinner = findViewById(R.id.yearSpinner);
        String selectedYear = yearSpinner.getSelectedItem().toString();
        Intent intentFromChooseMonth = new Intent(this, MonthlyReportActivity.class);
        intentFromChooseMonth.putExtra(MONTH_SELECTED,selectedMonthForQuery);
        intentFromChooseMonth.putExtra(YEAR_SELECTED,selectedYear);
        startActivity(intentFromChooseMonth);
    }
}
