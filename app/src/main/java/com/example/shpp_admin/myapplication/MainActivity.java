package com.example.shpp_admin.myapplication;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.shpp_admin.myapplication.R.id.buttonRight;

public class MainActivity extends AppCompatActivity {

    String HISTORIES = "HISTORIES";
    SharedPreferences sharedPreferences;

    RecyclerView rv;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    ArrayList<HistoryStrings> histories;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton buttonRight = (ImageButton) findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RightActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonLeft = (ImageButton) findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeftActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        sharedPreferences = getSharedPreferences("MAIN_NUMBERS", MODE_PRIVATE);
        String string = sharedPreferences.getString("MAIN_NUMBERS", "");

        Gson gson = new GsonBuilder().create();

        MainNumbers mainNumbers;
        if (!string.equals(""))
            mainNumbers = gson.fromJson(string, MainNumbers.class);
        else
            mainNumbers = new MainNumbers("1 gr", "1 gr", "1 days");

        // Вывод чисел на MainActivity
        TextView textPerDay = (TextView) findViewById(R.id.textPerDay);
        textPerDay.setText(mainNumbers.numberPerDay);
        TextView textTotal = (TextView) findViewById(R.id.textTotal);
        textTotal.setText(mainNumbers.numberTotal);
        TextView textDays = (TextView) findViewById(R.id.textDays);
        textDays.setText(mainNumbers.numberDays);

        rv = (RecyclerView)findViewById(R.id.rvHistory);
        manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        initializeData();
        adapter = new RecyclerAdapter(histories);
        rv.setAdapter(adapter);
    }

    private void initializeData() {
        histories = new ArrayList<>();

        sharedPreferences = getSharedPreferences("HISTORIES_INT", MODE_PRIVATE);
        int arrayLength = sharedPreferences.getInt("HISTORIES_INT", 0);

        Gson gson = new GsonBuilder().create();

        for (int i = 0; i < arrayLength; i++) {
            String HISTORIES = "HISTORIES_" + (i+1);

            sharedPreferences = getSharedPreferences(HISTORIES, MODE_PRIVATE);
            String mission = sharedPreferences.getString(HISTORIES, "Empty");

            HistoryStrings historiesClass = gson.fromJson(mission, HistoryStrings.class);
            histories.add(historiesClass);
        }
    }

    public void onCleanClick(View view) {
        sharedPreferences = getSharedPreferences("HISTORIES_INT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HISTORIES_INT", 0);
        editor.apply();

        histories.clear();
        adapter = new RecyclerAdapter(histories);
        rv.setAdapter(adapter);
    }
}

    /* Это маленький кусок кода, который рано или поздно кто-то удалит

    // Подключение RecyclerView в onCreate
        rvHistory = (RecyclerView) findViewById(R.id.rvHistory);
        manager = new LinearLayoutManager(this);
        rvHistory.setLayoutManager(manager);
        initializeData();
        adapter = new HistoryAdapter(histories);
        rvHistory.setAdapter(adapter);

    private void initializeData() {
        histories = new ArrayList<>();

        sharedPreferences = getSharedPreferences("HISTORIES_INT", MODE_PRIVATE);
        int total = sharedPreferences.getInt("HISTORIES_INT", 0);
        HISTORIES = "HISTORIES_" + total;
        sharedPreferences = getSharedPreferences(HISTORIES, MODE_PRIVATE);

        Gson gson = new GsonBuilder().create();
        for (int i = 0; i < total; i++) {
            HISTORIES = "HISTORIES_" + (i + 1);
            sharedPreferences = getSharedPreferences(HISTORIES, MODE_PRIVATE);

            String historyString = sharedPreferences.getString(HISTORIES, "EmptyHistory");
            HistoryStrings historyClass = gson.fromJson(historyString, HistoryStrings.class);
            histories.add(historyClass);
        }
    }

    public void onAddHistoryClick(View view) {
        // Создание ссылок на поля ввода
        EditText value = (EditText) findViewById(R.id.TextCena);
        EditText category = (EditText) findViewById(R.id.TextKategoryG);

        HistoryStrings historyClass = new HistoryStrings(value.getText().toString(), category.getText().toString());
        Gson gson = new GsonBuilder().create();
        String historyString = gson.toJson(historyClass, HistoryStrings.class);

        sharedPreferences = getSharedPreferences("HISTORIES_INT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int total = sharedPreferences.getInt("HISTORIES_INT", 0) + 1;
        editor.putInt("HISTORIES_INT", total);
        editor.apply();

        HISTORIES = "HISTORIES_" + total;
        sharedPreferences = getSharedPreferences(HISTORIES, MODE_PRIVATE);

        editor = sharedPreferences.edit();
        editor.putString(HISTORIES, historyString);
        editor.apply();

        initializeData();
        adapter = new HistoryAdapter(histories);
        rvHistory.setAdapter(adapter);
        value.setText("");
        category.setText("");
    }

    */

//jmyujmum
//gitignore



