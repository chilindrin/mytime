package com.chilin.org.stempeln;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.MainActivity;
import com.chilin.org.R;
import com.chilin.org.advice.AdviceUser;
import com.chilin.org.constants.Operation;
import com.chilin.org.exception.MyTimeException;
import com.chilin.org.util.DateTimeOperator;
import com.chilin.org.util.TextProcessor;

import java.time.LocalDateTime;

import static com.chilin.org.MainActivity.CURENT_DATE_OBJEKT;

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
        Intent intentFromMainActivity = getIntent();
        this.currentDateFromMainDisplay = (LocalDateTime) intentFromMainActivity
                .getSerializableExtra(CURENT_DATE_OBJEKT);
        setOperationToDo();
        setUpButtonInToolbar();
        setTimesInSpinner();
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
    private void setTimesInSpinner(){
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stempel_coming, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
            String friendlyCurrentDateMainDisplay = DateTimeOperator.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveComingTime(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveBeginnPause() {
        try {
            String friendlyCurrentDateMainDisplay = DateTimeOperator.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveBeginnPause(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveEndePause() {
        try {
            String friendlyCurrentDateMainDisplay = DateTimeOperator.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveEndePause(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveLeavingTime() {
        try {
            String friendlyCurrentDateMainDisplay = DateTimeOperator.getFriendlyFormatCurrentDate(this.currentDateFromMainDisplay);
            timeRegister.saveLeavingTime(friendlyCurrentDateMainDisplay,getTimeToBook());
            finish();
        } catch (MyTimeException ex){
            new AdviceUser().showBeginnPauseAfterLeavingTime(this,ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getTimeToBook(){
        Spinner selectingTime = findViewById(R.id.spinner);
        return selectingTime.getSelectedItem().toString();
    }

}
