package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var buttonA: Button
    private lateinit var buttonB: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        buttonA = findViewById(R.id.buttonA)
        buttonB = findViewById(R.id.buttonB)

        buttonA.setOnClickListener {
            val intent = Intent(this, ScreenAActivity::class.java).apply {
                putExtra("firstName", firstNameInput.text.toString())
                putExtra("lastName", lastNameInput.text.toString())
            }
            startActivity(intent)
        }

        buttonB.setOnClickListener {
            val intent = Intent(this, ScreenBActivity::class.java)
            startActivity(intent)
        }
    }
}
