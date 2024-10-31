package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScreenCActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var birthDateTextView: TextView
    private lateinit var buttonToMain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_c)

        nameTextView = findViewById(R.id.nameTextView)
        birthDateTextView = findViewById(R.id.birthDateTextView)
        buttonToMain = findViewById(R.id.buttonToMain)

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val birthDate = intent.getStringExtra("birthDate")

        nameTextView.text = "$firstName $lastName"
        birthDateTextView.text = "Дата рождения: $birthDate"

        buttonToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
