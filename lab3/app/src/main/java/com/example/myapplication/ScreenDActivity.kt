package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ScreenDActivity : AppCompatActivity() {

    private lateinit var buttonToB: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_d)

        buttonToB = findViewById(R.id.buttonToB)

        buttonToB.setOnClickListener {
            val intent = Intent(this, ScreenBActivity::class.java)
            startActivity(intent)
        }
    }
}
