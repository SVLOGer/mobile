package com.example.myapplication

import StudentManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val studentManager = StudentManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStudentInfo = findViewById<EditText>(R.id.inputStudentInfo)
        val removeIdInput = findViewById<EditText>(R.id.removeIdInput)
        val updatePointsInput = findViewById<EditText>(R.id.updatePointsInput)
        val renameInput = findViewById<EditText>(R.id.renameInput)
        val outputText = findViewById<TextView>(R.id.outputText)

        findViewById<Button>(R.id.addButton).setOnClickListener {
            val studentInfo = inputStudentInfo.text.toString().trim()
            val students = studentInfo.split(",").map { it.trim() }
            try {
                for (info in students) {
                    studentManager.addStudent(info)
                }
                outputText.text = "Студенты добавлены."
            } catch (e: Exception) {
                outputText.text = e.message
            }
        }

        findViewById<Button>(R.id.removeButton).setOnClickListener {
            val id = removeIdInput.text.toString().toIntOrNull()
            if (id != null) {
                studentManager.removeStudent(id)
                outputText.text = "Студент с ID $id удалён."
            } else {
                outputText.text = "Введите корректный ID."
            }
        }

        findViewById<Button>(R.id.updateButton).setOnClickListener {
            val parts = updatePointsInput.text.toString().trim().split(" ")
            if (parts.size == 2) {
                val id = parts[0].toIntOrNull()
                val newPoints = parts[1].toIntOrNull()
                if (id != null && newPoints != null) {
                    studentManager.updatePoints(id, newPoints)
                    outputText.text = "Баллы обновлены."
                } else {
                    outputText.text = "Введите корректный ID и новые баллы."
                }
            } else {
                outputText.text = "Введите данные в формате: ID НОВЫЕ БАЛЛЫ."
            }
        }

        findViewById<Button>(R.id.renameButton).setOnClickListener {
            val parts = renameInput.text.toString().trim().split(" ")
            if (parts.size == 2) {
                val id = parts[0].toIntOrNull()
                val newName = parts[1]
                if (id != null) {
                    studentManager.renameStudent(id, newName)
                    outputText.text = "Студент переименован."
                } else {
                    outputText.text = "Введите корректный ID."
                }
            } else {
                outputText.text = "Введите данные в формате: ID НОВОЕ ИМЯ."
            }
        }

        findViewById<Button>(R.id.printByPointsButton).setOnClickListener {
            val output = studentManager.printSortedByPoints()
            outputText.text = if (output.isNotEmpty()) output else "Нет студентов."
        }

        findViewById<Button>(R.id.printByNamesButton).setOnClickListener {
            val output = studentManager.printSortedByNames()
            outputText.text = if (output.isNotEmpty()) output else "Нет студентов."
        }
    }
}
