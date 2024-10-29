package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputCommand = findViewById<EditText>(R.id.inputCommand)
        val executeButton = findViewById<Button>(R.id.executeButton)
        val outputText = findViewById<TextView>(R.id.outputText)

        executeButton.setOnClickListener {
            val command = inputCommand.text.toString()
            val result = executeCommand(command)
            outputText.text = result
        }
    }

    private fun executeCommand(command: String): String {
        val tokens = command.split(" ")

        return when (tokens[0]) {
            "sum" -> {
                val a = tokens[1].toInt()
                val b = tokens[2].toInt()
                "Сумма чисел $a и $b равна ${sum(a, b)}"
            }
            "subtract" -> {
                val a = tokens[1].toInt()
                val b = tokens[2].toInt()
                "Разность чисел $a и $b равна ${subtract(a, b)}"
            }
            "divide" -> {
                val a = tokens[1].toInt()
                val b = tokens[2].toInt()
                "Частное чисел $a и $b равно ${divide(a, b)}"
            }
            "multiply" -> {
                val a = tokens[1].toInt()
                val b = tokens[2].toInt()
                "Произведение чисел $a и $b равно ${multiply(a, b)}"
            }
            "pow" -> {
                val a = tokens[1].toDouble()
                val b = tokens[2].toDouble()
                "$a в степени $b равно ${pow(a, b)}"
            }
            "max" -> {
                val numbers = tokens.drop(1).map { it.toInt() }
                "Максимальное число: ${max(numbers)}"
            }
            "min" -> {
                val numbers = tokens.drop(1).map { it.toInt() }
                "Минимальное число: ${min(numbers)}"
            }
            "print_list" -> {
                val numbers = tokens.drop(1).map { it.toInt() }
                printList(numbers)
            }
            "print_about" -> {
                val name = tokens[1]
                val age = tokens[2].toInt()
                printAbout(name, age)
            }
            else -> "Неизвестная команда"
        }
    }

    private fun sum(a: Int, b: Int): Int = a + b
    private fun subtract(a: Int, b: Int): Int = a - b
    private fun divide(a: Int, b: Int): Int = a / b
    private fun multiply(a: Int, b: Int): Int = a * b
    private fun pow(a: Double, b: Double): Double = a.pow(b)
    private fun max(numbers: List<Int>): Int = numbers.maxOrNull() ?: 0
    private fun min(numbers: List<Int>): Int = numbers.minOrNull() ?: 0
    private fun printList(numbers: List<Int>): String = numbers.sorted().joinToString(" < ")
    private fun printAbout(name: String, age: Int): String {
        return "Привет, меня зовут $name, мне $age лет, через 5 лет мне будет ${age + 5} лет."
    }
}
