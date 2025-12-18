package com.example.a2kd;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw = findViewById(R.id.swAlarm);

        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent i = new Intent();

                i.setComponent(new ComponentName(
                        "com.example.a2kdpart2",
                        "com.example.a2kdpart2.MainActivity"
                ));
                startActivity(i);

                sw.setChecked(false);
            }
        });
    }
}
