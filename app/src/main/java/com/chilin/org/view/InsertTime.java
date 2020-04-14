package com.chilin.org.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chilin.org.MainActivity;
import com.chilin.org.R;
import com.chilin.org.constants.Operation;
import com.chilin.org.util.TextProcessor;

public class InsertTime extends AppCompatActivity {

    private Operation operationForThisActivity;
    private int prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_time);
        setOperationToDo();
        setUpButtonInToolbar();
        showTimePickerDialog();
    }

    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void setOperationToDo() {
        Intent intent = getIntent();
        this.operationForThisActivity = (Operation) intent.getSerializableExtra(MainActivity.OPERATION);
        TextView textField = findViewById(R.id.textView3);
        textField.setText(TextProcessor.getText(this.operationForThisActivity));
    }

    private void setUpButtonInToolbar() {
        Toolbar insertTimeToolbar = findViewById(R.id.insertTimeToolbar);
        setSupportActionBar(insertTimeToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void setPrueba(int prueba){
        this.prueba = prueba;
    }

}
