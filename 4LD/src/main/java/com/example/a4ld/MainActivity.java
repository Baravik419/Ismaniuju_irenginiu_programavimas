package com.example.a4ld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    WebView webView;
    String startingAddress, enteredAddress;

    boolean backToastShown = false;

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

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText_url);
        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());

        startingAddress = "https://www.google.com/";
        webView.loadUrl(startingAddress);

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String lastAddress = sharedPreferences.getString("lastAddress", "");
        if (lastAddress != null && !lastAddress.isEmpty()) {
            webView.loadUrl(lastAddress);
            editText.setText(lastAddress);
        } else {
            String html =
                    "<html><body>" +
                            "<p>Paspauskite du kartus ant įvedimo lango jeigu reikalingas https://www</p>" +
                            "</body></html>";

            webView.loadData(html, "text/html; charset=utf-8", "UTF-8");
        }

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isBlank()) {
                    editText.setText("https://www.");
                    editText.setSelection(editText.getText().length());

                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredAddress = editText.getText().toString();
                webView.loadUrl(enteredAddress);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lastAddress", enteredAddress);
                editor.apply();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EB", "onDestroy() buvo iškviestas");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("EB", "onRestart() buvo iškviestas");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (webView.canGoBack()) {
                webView.goBack();
                backToastShown = false;
                Log.d("EB", "Grįžtame į prieš tai buvusį interneto puslapį");
                return true;
            } else{
                if (!backToastShown) {
                    Toast.makeText(this, "Kitą kartą paspaudus ATGAL bus uždarytas naršyklės langas", Toast.LENGTH_SHORT).show();
                    backToastShown = true;
                    return true;
                } else{
                    return super.onKeyDown(keyCode, event);
                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}