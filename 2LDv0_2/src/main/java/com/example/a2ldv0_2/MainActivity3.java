package com.example.a2ldv0_2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button; //mygtuku biblioteka
import android.widget.EditText;
import android.widget.TextView; //teksto biblioteka

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    TextView textView;

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String result = MainActivity.resultIntent.getStringExtra("text");
        textView = findViewById(R.id.textView_akt_3);
        textView.setText(result);

        button1 = findViewById(R.id.button1_akt_3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = result;
                int wordCount = text.isEmpty() ? 0 : text.split("\\s+").length;

                String overall_result = result + "\n" + "Žodžių skaičius: " + wordCount;
                textView.setText(overall_result);
                MainActivity.resultIntent.putExtra("zodziu_skaicius", overall_result);
                finish();
            }
        });
    }
}
