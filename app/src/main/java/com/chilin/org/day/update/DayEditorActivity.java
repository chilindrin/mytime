package com.chilin.org.day.update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.R;
import com.chilin.org.db.stempel.DBWriter;
import com.chilin.org.model.Day;
import com.chilin.org.util.TextProcessor;

import org.apache.commons.lang3.StringUtils;

public class DayEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_editor);
        setUpButtonInToolbar();
        setValuesToChange();
    }

    private void setValuesToChange() {
        Intent chooseDayToChangeIntent = getIntent();
        Bundle extrasFromChooseDayToChangeActivity = chooseDayToChangeIntent.getExtras();
        Day registeredDayToChange = (Day) extrasFromChooseDayToChangeActivity.getSerializable(ChooseDayToChange.DAY_TO_CHANGE);
        String dayRegistered = registeredDayToChange.getDayRegistered();
        String comingTime = registeredDayToChange.getComingTime();
        String beginnPause = registeredDayToChange.getBeginnPause();
        String endePause = registeredDayToChange.getEndePause();
        String leavingTime = registeredDayToChange.getLeavingTime();

        TextView dayToChangeValueView = findViewById(R.id.dayToChangeValue);
        dayToChangeValueView.setText(StringUtils.isNotBlank(dayRegistered)
                ? dayRegistered : "<->");

        TextView dailyKommen = findViewById(R.id.comingTimeToChangeValue);
        dailyKommen.setText(StringUtils.isNotBlank(comingTime)
                ? comingTime : "<->");

        TextView dailyBeginnPause = findViewById(R.id.beginnPauseToChangeValue);
        dailyBeginnPause.setText(StringUtils.isNotBlank(beginnPause)
                ? beginnPause : "<->");

        TextView dailyEndePause = findViewById(R.id.endPauseToChangeValue);
        dailyEndePause.setText(StringUtils.isNotBlank(endePause)
                ? endePause : "<->");

        TextView dailyGehen = findViewById(R.id.leavingTimeToChangeValue);
        dailyGehen.setText(StringUtils.isNotBlank(leavingTime)
                ? leavingTime : "<->");
    }

    private void setUpButtonInToolbar() {
        Toolbar dayEditorToolbar = findViewById(R.id.dayEditorToolbar);
        setSupportActionBar(dayEditorToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void saveDay(View view){
        TextView dayToChangeValueView = findViewById(R.id.dayToChangeValue);
        String registeredDay = (String) dayToChangeValueView.getText();

        EditText dailyKommen = findViewById(R.id.comingTimeToChangeValue);
        String newComingTime = TextProcessor.isNotPresent(dailyKommen.getText().toString())
                ? StringUtils.EMPTY : dailyKommen.getText().toString();

        EditText dailyBeginnPause = findViewById(R.id.beginnPauseToChangeValue);
        String newBeginnPause = TextProcessor.isNotPresent(dailyBeginnPause.getText().toString())
                ? StringUtils.EMPTY : dailyBeginnPause.getText().toString();

        EditText dailyEndePause = findViewById(R.id.endPauseToChangeValue);
        String newEndePause = TextProcessor.isNotPresent(dailyEndePause.getText().toString())
                ? StringUtils.EMPTY : dailyEndePause.getText().toString();

        EditText dailyGehen = findViewById(R.id.leavingTimeToChangeValue);
        String newLeavingTime = TextProcessor.isNotPresent(dailyGehen.getText().toString())
                ? StringUtils.EMPTY : dailyGehen.getText().toString();

        Day changedDay = new Day();
        changedDay.setDayRegistered(registeredDay);
        changedDay.setComingTime(newComingTime);
        changedDay.setBeginnPause(newBeginnPause);
        changedDay.setEndePause(newEndePause);
        changedDay.setLeavingTime(newLeavingTime);

        DBWriter dbWriter = new DBWriter(this);
        dbWriter.updateDay(changedDay);

        finish();
    }
}
