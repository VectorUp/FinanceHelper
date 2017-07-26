package com.example.shpp_admin.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class RightActivity extends AppCompatActivity{

    DialogFragment dlgCreation;

    SharedPreferences sharedPreferences;

    RecyclerView rv;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    ArrayList<MissionStrings> missions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);

        dlgCreation = new DialogActivity();

        rv = (RecyclerView)findViewById(R.id.rv);
        manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        initializeData();
        adapter = new RecyclerAdapter(missions);
        rv.setAdapter(adapter);
    }

    public void onCommitClick(View view) {
        EditText editTotal = (EditText) findViewById(R.id.totalNumber);
        EditText editDays = (EditText) findViewById(R.id.totalDays);
        EditText editEconomy = (EditText) findViewById(R.id.totalEconomy);

        int total = ValueCheck(editTotal);
        int days = ValueCheck(editDays);
        int economy = ValueCheck(editEconomy);
        int per_day = 0;

        if (days != 0) per_day = (total-economy)/days;

        Intent intent = new Intent(RightActivity.this, MainActivity.class);
        intent.putExtra("per_day", String.valueOf(per_day) + " gr");
        intent.putExtra("total", String.valueOf(total) + " gr");
        intent.putExtra("days", String.valueOf(days)+ " days");
        startActivity(intent);
    }

    public void onClick(View view) {
        dlgCreation.show(getFragmentManager(), "dlgCreation");
    }

    private void initializeData() {
        missions = new ArrayList<>();

        sharedPreferences = getSharedPreferences("MISSIONS_INT", MODE_PRIVATE);
        int arrayLength = sharedPreferences.getInt("MISSIONS_INT", 0);

        Gson gson = new GsonBuilder().create();

        for (int i = 0; i < arrayLength; i++) {
            String MISSION = "MISSIONS_" + (i+1);

            sharedPreferences = getSharedPreferences(MISSION, MODE_PRIVATE);
            String mission = sharedPreferences.getString(MISSION, "Empty");

            MissionStrings missionStrings = gson.fromJson(mission, MissionStrings.class);
            missions.add(missionStrings);
        }
    }

    private int ValueCheck (EditText editText) {
        String string = editText.getText().toString();
        if (string.equals("")) return 0;
        else return Integer.parseInt(string);
    }


//    activity lifecycle
//    extends/implements
//    recycler view
}
