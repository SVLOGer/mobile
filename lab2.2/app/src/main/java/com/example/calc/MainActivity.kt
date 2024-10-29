package com.example.calc
import android.graphics.Color

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import java.lang.Exception
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expressionTextView = findViewById(R.id.expressionTextView)
        resultTextView = findViewById(R.id.resultTextView)

        setUpButtons()
    }

    private fun setUpButtons() {
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnComma, R.id.btnEqual, R.id.btnDelete
        )

        buttons.forEach { buttonId ->
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { handleButtonClick(button) }
        }
    }


    private fun handleButtonClick(button: Button) {
        when (button.id) {
            R.id.btnEqual -> calculateResult()
            R.id.btnDelete -> {
                if (expression.isNotEmpty()) {
                    expression = expression.dropLast(1)
                    updateExpressionText()
                }
            }
            else -> {
                expression += button.text
                updateExpressionText()
            }
        }
    }

    private fun updateExpressionText() {
        expressionTextView.text = expression
    }

    private fun evaluateExpression(expression: String): Pair<String, Boolean> {
        val newExpression = expression.replace("×", "*").replace("−", "-").replace(",", ".")
        val exp = Expression(newExpression)
        val result = exp.calculate()
        return if (result.isNaN()) {
            Pair(getString(R.string.error), true) // true означает ошибку
        } else {
            Pair(result.toString().replace(".", ","), false) // false означает успешное вычисление
        }
    }

    private fun calculateResult() {
        try {
            val (result, isError) = evaluateExpression(expression)
            resultTextView.text = result

            val textColor = if (isError) Color.RED else Color.BLACK
            resultTextView.setTextColor(textColor)
            expressionTextView.setTextColor(textColor)
        } catch (e: Exception) {
            resultTextView.text = getString(R.string.error)
            resultTextView.setTextColor(Color.RED)
            expressionTextView.setTextColor(Color.RED)
        }
    }
}
