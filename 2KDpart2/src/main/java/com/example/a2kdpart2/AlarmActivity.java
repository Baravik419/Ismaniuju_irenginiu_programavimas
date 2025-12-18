package com.example.a2kdpart2;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);

        Button btnTurnSoundOff = findViewById(R.id.button1);
        Button btnClose = findViewById(R.id.button2);
        btnClose.setOnClickListener(v -> finishAffinity());

        btnTurnSoundOff.setOnClickListener(v -> {
            try {
                if (MyReceiver.player != null) {
                    if (MyReceiver.player.isPlaying()) MyReceiver.player.stop();
                    MyReceiver.player.release();
                    MyReceiver.player = null;
                }
            } catch (Exception ignored) {}
        });

    }
}
