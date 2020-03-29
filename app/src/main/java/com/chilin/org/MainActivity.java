package com.chilin.org;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.db.TimeRegister;
import com.chilin.org.util.DateProvider;
import com.chilin.org.view.AdviceUser;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    private TimeRegister timeRegister;
    private LocalDateTime currentDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.timeRegister = new TimeRegister();
        setCurrentDateOnDisplay();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.secureData:
                secureData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void secureData() {
        Intent intent = new Intent(this, DriveActivity.class);
        intent.putExtra("test", "test");
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCurrentDateOnDisplay() {
        this.currentDate = DateProvider.getCurrentDate();
        TextView currentDateTextView = findViewById(R.id.currentDateTextView);
        currentDateTextView.setText(DateProvider.getFriendlyFormatCurrentDate(this.currentDate));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveLeavingTime(View view) {
        if (DateProvider.isWeekend(this.currentDate)){
            new AdviceUser().showSorryWeekend(view.getContext());
        } else {
            TextView textViewCurrentDate = findViewById(R.id.currentDateTextView);
            String currentDate = (String) textViewCurrentDate.getText();
            timeRegister.saveLeavingTime(view.getContext(), currentDate);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveComingTime(View view){
        if (DateProvider.isWeekend(this.currentDate)){
            new AdviceUser().showSorryWeekend(view.getContext());
        } else {
            TextView textViewCurrentDate = findViewById(R.id.currentDateTextView);
            String currentDate = (String) textViewCurrentDate.getText();
            timeRegister.saveComingTime(view.getContext(), currentDate);
        }
    }

    public void report(View view){
        Intent intent = new Intent(this, CheckActivity.class);
        startActivity(intent);
    }

}
