package com.example.a1kd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView listView;
    TextView textViewSelected;
    Button buttonConfirm;
    ArrayList<String> selectedItems = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listView_fragmentList);
        textViewSelected = findViewById(R.id.textView_fragmentList);
        buttonConfirm = findViewById(R.id.button_akt_2);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = (String) parent.getItemAtPosition(position);
            selectedItems.add(item);

            StringBuilder sb = new StringBuilder();
            for (String s : selectedItems) {
                sb.append(s).append("\n");
            }
            String all = sb.toString();
            textViewSelected.setText(sb.toString());

            if (MainActivity.resultIntent == null) {
                MainActivity.resultIntent = new Intent();
            }
            MainActivity.resultIntent.putExtra("allSelected", all);
        });

        buttonConfirm.setOnClickListener(v -> {
            finish();
        });

    }

}
