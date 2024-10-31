package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ScreenBActivity : AppCompatActivity() {

    private lateinit var buttonToMain: Button
    private lateinit var buttonToD: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_b)

        buttonToMain = findViewById(R.id.buttonToMain)
        buttonToD = findViewById(R.id.buttonToD)

        buttonToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonToD.setOnClickListener {
            val intent = Intent(this, ScreenDActivity::class.java)
            startActivity(intent)
        }
    }
}
