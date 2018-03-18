package com.example.henriktre.lab2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class A2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);
        setupNumberPicker();
        getUserPreference();
        createSharedPreferences();
        back();
    }
    private void setupNumberPicker() {
        NumberPicker limit = findViewById(R.id.feedNumberPicker);
        NumberPicker frequency = findViewById(R.id.refreshRatePicker);

        limit.setMinValue(1);
        limit.setMaxValue(20);
        limit.setWrapSelectorWheel(false);

        frequency.setMinValue(1);
        frequency.setMaxValue(10);
        frequency.setWrapSelectorWheel(false);
    }
    private void createSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("cache", MODE_PRIVATE);

        NumberPicker limitItems = findViewById(R.id.feedNumberPicker);
        NumberPicker frequency = findViewById(R.id.refreshRatePicker);
        EditText rssUrl = findViewById(R.id.rssText);

        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("limitItems", limitItems.getValue());
        prefEditor.putInt("frequency", frequency.getValue());
        prefEditor.putString("URL", rssUrl.getText().toString());

        prefEditor.apply();
    }

    private void getUserPreference() {
        SharedPreferences sharedPref = getSharedPreferences("cache", MODE_PRIVATE);
        int limit = sharedPref.getInt("limitItems",-1);
        int freq = sharedPref.getInt("frequency", -1);
        String url = sharedPref.getString("URL", "");

        NumberPicker limitItems = findViewById(R.id.feedNumberPicker);
        NumberPicker frequency = findViewById(R.id.refreshRatePicker);
        EditText rssUrl = findViewById(R.id.rssText);

        limitItems.setValue(limit);
        frequency.setValue(freq);
        rssUrl.setText(url);
    }

    private void back() {


        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText rssText = findViewById(R.id.rssText);
                NumberPicker feedNumberPicker = findViewById(R.id.feedNumberPicker);
                NumberPicker refreshRatePicker = findViewById(R.id.refreshRatePicker);
                Intent intent = new Intent(A2Activity.this, A1Activity.class);
                intent.putExtra("rssText", rssText.getText().toString());
                intent.putExtra("itemLimit", feedNumberPicker.getValue());
                intent.putExtra("frequency", refreshRatePicker.getValue());
                startActivity(intent);
            }
        });
    }

}
