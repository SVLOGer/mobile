package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity

class ScreenAActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var datePicker: DatePicker
    private lateinit var buttonToC: Button
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_a)

        nameTextView = findViewById(R.id.nameTextView)
        datePicker = findViewById(R.id.datePicker)
        buttonToC = findViewById(R.id.buttonToC)

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        nameTextView.text = "$firstName $lastName"

        datePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }

        buttonToC.setOnClickListener {
            val intent = Intent(this, ScreenCActivity::class.java).apply {
                putExtra("firstName", firstName)
                putExtra("lastName", lastName)
                putExtra("birthDate", selectedDate)
            }
            startActivity(intent)
        }
    }
}
