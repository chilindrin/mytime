package com.chilin.org.abruf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.R;
import com.chilin.org.db.abruf.Writer;
import com.chilin.org.model.Abruf;

public class AbrufErstellerActivity extends AppCompatActivity {

    private Writer abrufWritter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abruf_ersteller);
        setUpButtonInToolbar();
    }

    private void setUpButtonInToolbar() {
        Toolbar createAbrufToolBar = findViewById(R.id.createAbrufToolbar);
        setSupportActionBar(createAbrufToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void createAbruf(View view){
        TextView abrufNameTextView = findViewById(R.id.abrufName);
        String abrufNameFromUser = (String) abrufNameTextView.getText().toString();
        TextView abrufBeginnTextView = findViewById(R.id.abrufBeginn);
        String abrufBeginnFromUser = (String) abrufBeginnTextView.getText().toString();
        TextView abrufEndeTextView = findViewById(R.id.abrufEnde);
        String abrufEndeFromUser = (String) abrufEndeTextView.getText().toString();
        TextView abrufStundenTextView = findViewById(R.id.abrufStunden);
        String abrufStundenFromUser = (String) abrufStundenTextView.getText().toString();

        Abruf abrufToCreate = new Abruf();
        abrufToCreate.setName(abrufNameFromUser);
        abrufToCreate.setAnfang(abrufBeginnFromUser);
        abrufToCreate.setEnde(abrufEndeFromUser);
        abrufToCreate.setStunden(abrufStundenFromUser);

        getAbrufWritter().createAbruf(abrufToCreate);
        finish();
    }

    private Writer getAbrufWritter(){
        if (this.abrufWritter == null){
            this.abrufWritter = new Writer(this);
        }
        return this.abrufWritter;
    }
}
