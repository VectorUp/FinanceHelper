package com.example.shpp_admin.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class RightActivity extends AppCompatActivity{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<HistoryStrings> historyStringses;
    int totalSpending = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);

        historyStringses = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayList<String> spendings = new ArrayList<>();

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.list_item, spendings);
        listView.setAdapter(adapter);

        ImageButton buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editValue = (EditText) findViewById(R.id.editValue);
                EditText editCategory = (EditText) findViewById(R.id.editCategory);

                if (!(editValue.getText().toString().equals("") || editCategory.getText().toString().equals(""))) {
                    HistoryStrings historyStrings = new HistoryStrings(editCategory.getText().toString(),
                            editValue.getText().toString());
                    historyStringses.add(historyStrings);
                    totalSpending += Integer.parseInt(historyStrings.value);

                    String spending = editCategory.getText().toString() + " " + editValue.getText().toString();
                    spendings.add(spending);
                    adapter.notifyDataSetChanged();
                    editValue.setText(""); editCategory.setText("");
                }
            }
        });

        TextView buttonOK = (TextView) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder().create();
                for (int i = 0; i < spendings.size(); i++) {
                    sharedPreferences = getSharedPreferences("HISTORIES_INT", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    int historiesInt = sharedPreferences.getInt("HISTORIES_INT", 0) + 1;
                    editor.putInt("HISTORIES_INT", historiesInt);
                    editor.apply();

                    String HISTORIES = "HISTORIES_" + historiesInt;
                    sharedPreferences = getSharedPreferences(HISTORIES, MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    String historyString = gson.toJson(historyStringses.get(i), HistoryStrings.class);
                    editor.putString(HISTORIES, historyString);
                    editor.apply();
                }

                sharedPreferences = getSharedPreferences("MAIN_NUMBERS", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                String mainNumbersString = sharedPreferences.getString("MAIN_NUMBERS", "");
                MainNumbers mainNumbers = gson.fromJson(mainNumbersString, MainNumbers.class);

                mainNumbers.numberTotal = String.valueOf(Integer.parseInt(mainNumbers.numberTotal)
                        - totalSpending);
                mainNumbers.numberPerDay = String.valueOf(Integer.parseInt(mainNumbers.numberPerDay)
                        - totalSpending);

                mainNumbersString = gson.toJson(mainNumbers, MainNumbers.class);
                editor.putString("MAIN_NUMBERS", mainNumbersString);
                editor.apply();

                spendings.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}

//activity lifecycle
//extends/implements
//recycler view