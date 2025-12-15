package com.example.a7ld;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Switch swTracking;
    private Toolbar toolbar;

    private MyReceiver receiver;
    private boolean receiverRegistered = false;

    private SharedPreferences prefs;
    private static final String PREFS = "ld7";
    private static final String KEY_ENABLED = "enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);
        swTracking = findViewById(R.id.swTracking);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        requestNotificationPermission();

        boolean enabled = prefs.getBoolean(KEY_ENABLED, false);
        swTracking.setChecked(enabled);
        updateText(enabled);

        if (enabled) startTracking();

        swTracking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_ENABLED, isChecked).apply();
            updateText(isChecked);

            if (isChecked) startTracking();
            else stopTracking();
        });
    }

    private void startTracking() {
        if (receiver == null) {
            receiver = new MyReceiver();
        }
        if (!receiverRegistered) {
            registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            receiverRegistered = true;
        }
    }

    private void stopTracking() {
        if (receiverRegistered) {
            unregisterReceiver(receiver);
            receiverRegistered = false;
        }
    }

    private void updateText(boolean enabled) {
        textView.setText(enabled ? "Sekimas įjungtas" : "Sekimas išjungtas");
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100
                );
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTracking();
    }
}
