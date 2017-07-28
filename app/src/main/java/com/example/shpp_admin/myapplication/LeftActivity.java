package com.example.shpp_admin.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Timer;
import java.util.TimerTask;

public class LeftActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    Timer timer;
    TimerTask mTimerTask;

    TextView textDays;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left);

        textDays = (TextView) findViewById(R.id.textDays);
        editText = (EditText) findViewById(R.id.editDays);

        ImageButton buttonRight2 = (ImageButton) findViewById(R.id.buttonRight2);
        buttonRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeftActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonCommit = (ImageButton) findViewById(R.id.buttonCommit);
        buttonCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("MAIN_NUMBERS", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                EditText editTotal = (EditText) findViewById(R.id.editTotal);
                EditText editDays = (EditText) findViewById(R.id.editDays);
                EditText editEconomy = (EditText) findViewById(R.id.editEconomy);

                int total = ValueCheck(editTotal);
                int days = ValueCheck(editDays);
                int economy = ValueCheck(editEconomy);
                int perday = 0;

                if (days == 0)
                    perday = 0;
                else
                    perday = (total-economy)/days;

                MainNumbers mainNumbers = new MainNumbers(String.valueOf(perday),
                        String.valueOf(total), String.valueOf(days), perday);
                Gson gson = new GsonBuilder().create();
                String numbersString = gson.toJson(mainNumbers, MainNumbers.class);

                editor.putString("MAIN_NUMBERS", numbersString);
                editor.apply();
            }
        });
    }

    private int ValueCheck (EditText editText) {
        String string = editText.getText().toString();
        if (string.equals("")) return 0;
        else return Integer.parseInt(string);
    }
}