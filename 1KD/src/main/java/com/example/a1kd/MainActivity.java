package com.example.a1kd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;
    TextView textView;

    static Intent resultIntent;

    Intent intent1, intent2, getIntent;


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
        getIntent = getIntent();

        textView = findViewById(R.id.textView_akt_1);

        button1 = findViewById(R.id.button1_akt_1);
        button2 = findViewById(R.id.button2_akt_1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultIntent == null){
                    resultIntent = new Intent();
                }
                Intent openSecond = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(openSecond);
            }
        });

        button2.setOnClickListener(v -> {
            String text = textView.getText().toString();
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            intent.putExtra("resultText", textView.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        if (resultIntent == null) {
            Log.d("EB", "onRestart: resultIntent == null, dar nieko negauta iš antros veiklos");
            return;
        }

        String allSelected = resultIntent.getStringExtra("allSelected");

        if (allSelected != null && !allSelected.isEmpty()) {
            Log.d("EB", "onRestart: iš antros veiklos gautas tekstas: " + allSelected);
            textView.setText(allSelected);
        } else {
            Log.d("EB", "onRestart: 'allSelected' yra null arba tuščias");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String allSelected = data.getStringExtra("allSelected");
            textView.setText(allSelected);
        }
    }
}