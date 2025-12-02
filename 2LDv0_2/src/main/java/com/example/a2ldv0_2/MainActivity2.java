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

public class MainActivity2 extends AppCompatActivity {

    Button button1;
    EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText_akt_2);
        button1 = findViewById(R.id.button1_akt_2);

        if (MainActivity.resultIntent != null &&
                MainActivity.resultIntent.getStringExtra("text") != null) {

            String esamas = MainActivity.resultIntent.getStringExtra("text");
            editText.setText(esamas);
            editText.setSelection(esamas.length()); // kursorius gale
        }

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String result = editText.getText().toString();
                MainActivity.resultIntent.putExtra("text", result);
                MainActivity.resultIntent.putExtra("result", "OK");
                Log.d("DZ", "informacija gauta = " + result);
                finish();
            }
        });
    }
}