package com.example.a6ld;

import static java.lang.Math.abs;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import static com.example.a6ld.R.id.menuItemDifference;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3;
    AlertDialog.Builder alertDialogBuilder;

    Button button1, button2, button3;

    Toolbar toolbar;
    Gija gija;

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

        registerForContextMenu(findViewById(R.id.textView1));

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("6LD");
        toolbar.setSubtitle("Meniu komponentai");

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        alertDialogBuilder = new AlertDialog.Builder(this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gija != null){
                    if(gija.isAlive()){
                        gija.setToSleep(true);
                    } else{
                        textView3.setText("");
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gija != null){
                    if(gija.isAlive()){
                        gija.interrupt();
                    } else{
                        textView3.setText("");
                    }
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gija != null) {
                    if (gija.isAlive()) {
                        gija.setToFinish(true);
                    } else {
                        textView3.setText("");
                    }
                }
            }
        });

    }

    private void setTime(){
        final Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);
        int currentTimeInMinutes = currentHour * 60 + currentHour;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            int SelectedTimeInMinutes = hourOfDay * 60 + minute;
            int difference = abs(SelectedTimeInMinutes - currentTimeInMinutes);
            String result = "Skirtumas tarp dabar ir nurodyto laiko yra " + difference + " minutes";
            textView1.setText(result);
            AlertDialog alertDialog = createAlertDialog("Skirtumas tarp dabar ir nurodyto laiko yra ", result);
            alertDialog.show();
        }, currentHour, currentMinute, false);
        timePickerDialog.show();
    }

    private AlertDialog createAlertDialog(String title, String result){
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(result);
        return alertDialog;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int options = menuItemDifference;
        Log.d("EB", "Pasirinktas meniu elementas: "+ options);
        if(item.getItemId() == R.id.menuItemDifference){
            setTime();
            Log.d("EB", "Difference");
            return true;
        }
        else if(item.getItemId() == R.id.menuItemDifference1){
            Log.d("EB", "Difference1");
            return true;
        }
        else if(item.getItemId() == R.id.menuItemClose){
            Log.d("EB", "Close");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.contextMenu1){
            String result = "Tekste yra " + textView1.getText().toString().length() + " simboliu";
            Log.d("EB", "Simbolių skaičius" + result);
            textView2.setText(result);
        } else if (item.getItemId() == R.id.contextMenu2){
            Log.d("EB", "Skirtumas2");
            gija = new Gija(textView1.getText().toString(), textView3, this);
            gija.start();
        }
        return super.onContextItemSelected(item);
    }

}