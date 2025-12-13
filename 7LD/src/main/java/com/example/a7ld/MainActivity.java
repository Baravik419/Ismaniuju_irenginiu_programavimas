package com.example.a7ld;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button1, button2;
    Intent intent1, getIntent1;
    IntentFilter intentFilter1, intentFilter2;

    MyReceiver receiver1, receiver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        intentFilter1 = new IntentFilter("com.example.a7ld.manoIntentas");
        intentFilter2 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        receiver1 = new MyReceiver(MainActivity.this, textView);
        registerReceiver(receiver1, intentFilter1, RECEIVER_EXPORTED);

        receiver2 = new MyReceiver(MainActivity.this, textView);
        registerReceiver(receiver2, intentFilter2, RECEIVER_EXPORTED);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent();
                intent1.setAction("com.example.a7ld.manoIntentas");
                intent1.putExtra("pranesimas", "Intent1 pranesimas");
                sendBroadcast(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            }
        });
    }


}