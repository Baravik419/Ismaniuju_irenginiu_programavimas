package com.example.a2ldv0_2;

import android.os.Bundle;

//mano bibliotekos

import android.util.Log;
import android.view.View;
import android.widget.Button; //mygtuku biblioteka
import android.widget.TextView; //teksto biblioteka
import android.content.Intent; //intent biblioteka


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3;
    TextView textView;

    static Intent resultIntent;

    Intent intent2, intent3;

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

        textView = findViewById(R.id.textView_akt_1); //surisamas java kintamasis su xml teksto lauku

        button1 = findViewById(R.id.button1_akt_1); //surisamas java kintamasis su xml mygtuko lauku
        button2 = findViewById(R.id.button2_akt_1);
        button3 = findViewById(R.id.button3_akt_1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultIntent == null){
                    resultIntent = new Intent();
                }
                Intent openSecond = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(openSecond);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultIntent != null && resultIntent.getStringExtra("result") != null) {
                    intent2 = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(intent2);
                }
                else {
                    Log.d("DZ", "Nėra informacijos.");
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(resultIntent != null && resultIntent.getStringExtra("zodziu_skaicius") != null){
                intent3 = new Intent(Intent.ACTION_SEND);
                intent3.putExtra(Intent.EXTRA_TEXT, resultIntent.getStringExtra("zodziu_skaicius"));
                intent3.setType("text/plain");
                Intent chooser = Intent.createChooser(intent3, "Share");
                startActivity(chooser);
            }
            else {
                Log.d("DZ", "Nėra informacijos iš antros ar trečios veiklos.");
            }
            }
        });

    }

    @Override
    protected void onRestart(){
        super.onRestart();

        String result = resultIntent.getStringExtra("text").toString();
        Log.d("DZ", "Is antros veiklos gautas tekstas: " + resultIntent.getStringExtra("text"));
        textView.setText(result);

        if (resultIntent.getStringExtra("zodziu_skaicius") != null){
            if(resultIntent.getStringExtra("zodziu_skaicius").toString() != "") {
                result = resultIntent.getStringExtra("zodziu_skaicius").toString();
                Log.d("DZ", "Is trecios veiklos gautas tekstas: " + result);
                textView.setText(result);
            }
        }
    }
}