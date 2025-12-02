package com.example.a2ld

import android.os.Bundle
import android.widget.Button // mygtukų biblioteka
import android.widget.TextView // teksto biblioteka
import android.util.Log //
import android.view.View //
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {

    Button button1, button2, button3;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
