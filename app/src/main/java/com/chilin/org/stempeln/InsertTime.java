package com.chilin.org.stempeln;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.chilin.org.MainActivity;
import com.chilin.org.R;
import com.chilin.org.constants.Operation;
import com.chilin.org.db.TimeRegister;
import com.chilin.org.exception.MyTimeException;
import com.chilin.org.util.DateTimeOperationsProvider;
import com.chilin.org.util.TextProcessor;
import com.chilin.org.view.AdviceUser;

import java.time.LocalDateTime;

public class InsertTime extends AppCompatActivity {

    private Operation operationForThisActivity;
    private LocalDateTime currentDateFromMainDisplay;
    private TimeRegister timeRegister;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_time);
        this.timeRegister = new TimeRegister(this);
        setOperationToDo();
        setUpButtonInToolbar();
        setHoursAndMinutesToDisplay();
    }

    private void setOperationToDo() {
        Intent intent = getIntent();
        this.operationForThisActivity = (Operation) intent.getSerializableExtra(MainActivity.OPERATION);
        TextView operationToDoView = findViewById(R.id.operationToDoView);
        operationToDoView.setText(TextProcessor.getTextForOperation(this.operationForThisActivity));
    }

    private void setUpButtonInToolbar() {
        Toolbar insertTimeToolbar = findViewById(R.id.insertTimeToolbar);
        setSupportActionBar(insertTimeToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setHoursAndMinutesToDisplay(){
        Intent intentFromMainActivity = getIntent();
        currentDateFromMainDisplay = (LocalDateTime) intentFromMainActivity.getSerializableExtra(MainActivity.CURENT_DATE_OBJEKT);
        TextView hourTextView = findViewById(R.id.hourTextView);
        hourTextView.setText(String.valueOf(currentDateFromMainDisplay.getHour()));
        TextView minuteTextView = findViewById(R.id.minuteTextView);
        minuteTextView.setText(String.valueOf(currentDateFromMainDisplay.getMinute()));
    }

    public void changeTimeBeingDisplayed(View view){
        TextView hourTextView = findViewById(R.id.hourTextView);
        TextView minuteTextView = findViewById(R.id.minuteTextView);
        DialogFragment newFragment = new TimePickerFragment(hourTextView,minuteTextView);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveTimeInDB(View view){
        switch (this.operationForThisActivity){
            case COMING:
                saveComingTime();
                break;
            case BEGINN_PAUSE:
                saveBeginnPause();
                break;
            case ENDE_PAUSE:
                saveEndePause();
                break;
            case LEAVING:
                saveLeavingTime();
                break;
            default:
                throw new MyTimeException("There is no other operation to perform");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveComingTime() {
        try {
            String friendlyCurrentDateMainDisplay = DateTimeOperationsProvider.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveComingTime(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveBeginnPause() {
        try {
            String friendlyCurrentDateMainDisplay = DateTimeOperationsProvider.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveBeginnPause(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveEndePause() {
        try {
            String friendlyCurrentDateMainDisplay = DateTimeOperationsProvider.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveEndePause(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveLeavingTime() {
        try {
            String friendlyCurrentDateMainDisplay = DateTimeOperationsProvider.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveLeavingTime(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getTimeToBook(){
        TextView hoursView = findViewById(R.id.hourTextView);
        String selectedHours = (String) hoursView.getText();
        TextView minutesView = findViewById(R.id.minuteTextView);
        String selectedMinutes = (String) minutesView.getText();
        return TextProcessor.getTime(Integer.parseInt(selectedHours),Integer.parseInt(selectedMinutes));
    }

}
