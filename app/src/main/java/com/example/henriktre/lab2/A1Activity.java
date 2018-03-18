package com.example.henriktre.lab2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.NumberPicker;

public class A1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);
        setupNumberPicker();
        createSharedPreferences();
        getUserPreference();
    }
    private void setupNumberPicker() {
        // Get dropdowns from ids
        NumberPicker limit = findViewById(R.id.feedNumberPicker);
        NumberPicker frequency = findViewById(R.id.refreshRatePicker);

        // Set max and min values to limit items
        limit.setMinValue(1);
        limit.setMaxValue(20);
        limit.setWrapSelectorWheel(false);

        // Set max and min values to frequency
        frequency.setMinValue(1);
        frequency.setMaxValue(10);
        frequency.setWrapSelectorWheel(false);
    }
    private void createSharedPreferences() {
        // Get shared prefs
        SharedPreferences sharedPref = getSharedPreferences("FileName",0);

        // Find values from ID
        NumberPicker limitItems = findViewById(R.id.feedNumberPicker);
        NumberPicker frequency = findViewById(R.id.refreshRatePicker);
        EditText rssUrl = findViewById(R.id.rssText);

        // Save values to shared preferences
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("limitItems", limitItems.getValue());
        prefEditor.putInt("frequency", frequency.getValue());
        prefEditor.putString("URL", rssUrl.getText().toString());

        prefEditor.apply();
    }

    /**
     * getUserPreference gets prefs and sets the values to elements in the app
     * **/
    private void getUserPreference() {
        // Get shared prefs
        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        // Get values from shared prefs
        int limit= sharedPref.getInt("limitItems",-1);
        int freq = sharedPref.getInt("frequency", -1);
        String url = sharedPref.getString("URL", "");

        // Set get elements by id
        NumberPicker limitItems = findViewById(R.id.feedNumberPicker);
        NumberPicker frequency = findViewById(R.id.refreshRatePicker);
        EditText rssUrl = findViewById(R.id.rssText);

        // Set values to elements in the app
        limitItems.setValue(limit);
        frequency.setValue(freq);
        rssUrl.setText(url);
    }
/*
    private void back() {


        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText t1 = findViewById(R.id.T1);
                Intent intent = new Intent(A1Activity.this, A2Activity.class);
                intent.putExtra("T1", t1.getText().toString());
                startActivity(intent);
            }
        });
    }
*/

}
