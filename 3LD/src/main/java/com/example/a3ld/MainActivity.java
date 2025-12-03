package com.example.a3ld;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    AutoCompleteTextView autoCompleteTextView;
    RatingBar ratingBar;
    TimePicker timePicker;
    DatePicker datePicker;
    Spinner spinner;
    Switch switch1;

    Button button;

    boolean patvirtinimas;

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

        editText = findViewById(R.id.editText_vardas_ir_pavarde);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView_padaliniai);
        ratingBar = findViewById(R.id.ratingBar);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        datePicker = findViewById(R.id.datePicker);
        spinner = findViewById(R.id.spinner_miestas);
        switch1 = findViewById(R.id.switch_busena);
        button = findViewById(R.id.button_saugoti);

        /// Padaliniu string

        String[] padaliniai = getResources().getStringArray(R.array.padaliniai);

        ArrayAdapter<String> adapter_padaliniai = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, padaliniai);
        autoCompleteTextView.setAdapter(adapter_padaliniai);

        /// Miestu string

        String[] miestai = getResources().getStringArray(R.array.miestai);

        ArrayAdapter<String> adapter_miestai = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, miestai);
        spinner.setAdapter(adapter_miestai);


        button.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                String vardas_pavarde = editText.getText().toString();
                String padalinys = autoCompleteTextView.getText().toString();
                float ivertinimas = ratingBar.getRating();
                int valanda = timePicker.getHour();
                int minute = timePicker.getMinute();
                int diena = datePicker.getDayOfMonth();
                int menuo = datePicker.getMonth();
                int metai = datePicker.getYear();
                String miestas = "";
                if(spinner.getSelectedItem() != null){
                    miestas = spinner.getSelectedItem().toString();
                }
                boolean statusas = switch1.isChecked();

                String rezultatas = "Pavadinimas: " + vardas_pavarde + "; " + "sudėtingumas: " + ivertinimas + ";" + "\n" +
                        "Laikas: " + valanda + ":" + minute + ", " + "data: " + metai + "-" + menuo + "-" + diena + "\n" +
                        "Padalinys: " + padalinys + "; " + "Miestas: " + miestas;

                patvirtinimas = false;

                ///  Darom ta neaisku snackbar'a

                if(statusas){
                    Snackbar snackbar = Snackbar.make(view, rezultatas, 6000).setAction("Patvirtinu, informacija teisinga", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveToFile(vardas_pavarde, rezultatas, v);
                            patvirtinimas = true;
                        }
                    });

                    snackbar.addCallback(new Snackbar.Callback(){
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event){
                            if(!patvirtinimas && event != BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION){
                                Toast.makeText(MainActivity.this, "Laikas baigėsi, bandykite dar kartą išsaugoti duomenis", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                    snackbar.show();

                }

                else{
                    Toast.makeText(MainActivity.this, "Duomenys nebus išsaugoti", Toast.LENGTH_LONG).show();
                }

            }

        });


    }

    private void saveToFile(String name, String content, View v){
        String fileName = name.replaceAll("\\s+", "")+".txt";

        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)){
            fos.write(content.getBytes());

            Toast.makeText(MainActivity.this, "Duomenys įrašyti sėkmingai: " + getFilesDir() + "/" + fileName, Toast.LENGTH_LONG).show();
        } catch(IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Nenumatyta klaida išsaugant failą", Toast.LENGTH_SHORT).show();
        }

    }


}