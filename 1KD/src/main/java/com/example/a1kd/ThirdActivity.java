package com.example.a1kd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    TextView textView;
    Button buttonSend;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);

        textView = findViewById(R.id.textView_akt3);
        buttonSend = findViewById(R.id.button_akt_3);
        
        Intent intent = getIntent();
        String originalText = intent.getStringExtra("resultText");
        if (originalText == null){
            originalText ="";
        }

        int wordCount = 0;
        String trimmed = originalText.trim();
        if (!trimmed.isEmpty()) {
            String[] words = trimmed.split("\\s+");
            wordCount = words.length;
        }

        String displayText = originalText + "\n" + "Zodziu skaicius: " + wordCount;

        textView.setText(displayText);

        buttonSend.setOnClickListener(v -> {
            String textToSend = textView.getText().toString();

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);

            Intent shareIntent = Intent.createChooser(sendIntent, "Pasirinkite programą");
            startActivity(shareIntent);
        });


    }
}
