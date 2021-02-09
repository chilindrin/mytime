package com.chilin.org.day.delete;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.R;
import com.chilin.org.db.stempel.DBWriter;
import com.chilin.org.model.Day;

public class DayEraserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_eraser);
        setUpButtonInToolbar();
        displayDayToDelete();
    }

    private void displayDayToDelete() {
        Intent chooseDayToDeleteIntent = getIntent();
        Bundle extrasFromChooseDayToDeleteActivity = chooseDayToDeleteIntent.getExtras();
        Day registeredDayToDelete = (Day) extrasFromChooseDayToDeleteActivity.getSerializable(ChooseDayToDelete.DAY_TO_DELETE);

        TextView dayToDeleteValue = findViewById(R.id.dayToDeleteValue);
        dayToDeleteValue.setText(registeredDayToDelete.getDayRegistered());

        TextView comingTimeToDeleteValue = findViewById(R.id.comingTimeToDeleteValue);
        comingTimeToDeleteValue.setText(registeredDayToDelete.getComingTime());

        TextView beginPauseToDeleteValue = findViewById(R.id.beginPauseToDeleteValue);
        beginPauseToDeleteValue.setText(registeredDayToDelete.getBeginnPause());

        TextView endePauseToDeleteValue = findViewById(R.id.endePauseToDeleteValue);
        endePauseToDeleteValue.setText(registeredDayToDelete.getEndePause());

        TextView leavingTimeToDeleteValue = findViewById(R.id.leavingTimeToDeleteValue);
        leavingTimeToDeleteValue.setText(registeredDayToDelete.getLeavingTime());
    }

    private void setUpButtonInToolbar() {
        Toolbar dayEraserToolbar = findViewById(R.id.dayEraserToolbar);
        setSupportActionBar(dayEraserToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void deleteDay(View view){
        Intent chooseDayToDeleteIntent = getIntent();
        Bundle extrasFromChooseDayToDeleteActivity = chooseDayToDeleteIntent.getExtras();
        Day registeredDayToDelete = (Day) extrasFromChooseDayToDeleteActivity.getSerializable(ChooseDayToDelete.DAY_TO_DELETE);
        DBWriter dbWriter = new DBWriter(this);
        dbWriter.deleteDay(registeredDayToDelete.getDayRegistered());
        finish();
    }
}
