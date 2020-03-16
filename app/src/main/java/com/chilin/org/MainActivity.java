package com.chilin.org;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chilin.org.db.TimeRegister;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TimeRegister timeRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.timeRegister = new TimeRegister();
        setCurrentDateOnDisplay();
    }

    private void setCurrentDateOnDisplay() {
        TextView textViewCurrentDate = (TextView)findViewById(R.id.textView);
        textViewCurrentDate.setText(getCurrentDate());
    }

    private String getCurrentDate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(currentTime);
    }

    public void saveLeavingTime(View view) {
        TextView textViewCurrentDate = (TextView)findViewById(R.id.textView);
        String currentDate = (String) textViewCurrentDate.getText();
        timeRegister.saveLeavingTime(view.getContext(), currentDate);
    }

    public void saveComingTime(View view){
        TextView textViewCurrentDate = (TextView)findViewById(R.id.textView);
        String currentDate = (String) textViewCurrentDate.getText();
        timeRegister.saveComingTime(view.getContext(), currentDate);
    }

    public void deleteDB(View view){
        timeRegister.deleteDB(view.getContext());
    }

    public void report(View view){
        Intent intent = new Intent(this, CheckActivity.class);
        startActivity(intent);
    }

}
