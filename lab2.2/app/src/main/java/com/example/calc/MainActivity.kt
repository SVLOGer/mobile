package com.example.calc

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calc.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpButtons()
    }

    private fun setUpButtons() {
        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9,
            binding.btnPlus, binding.btnMinus, binding.btnMultiply, binding.btnDivide,
            binding.btnComma, binding.btnEqual, binding.btnDelete
        )

        buttons.forEach { button ->
            button.setOnClickListener { handleButtonClick(button) }
        }
    }

    private fun handleButtonClick(button: Button) {
        when (button.id) {
            binding.btnEqual.id -> calculateResult()
            binding.btnDelete.id -> {
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
        binding.expressionTextView.text = expression
    }

    private fun evaluateExpression(expression: String): Pair<String, Boolean> {
        val newExpression = expression.replace("×", "*").replace("−", "-").replace(",", ".")
        val exp = Expression(newExpression)
        val result = exp.calculate()
        return if (result.isNaN()) {
            Pair(getString(R.string.error), true)
        } else {
            Pair(result.toString().replace(".", ","), false)
        }
    }

    private fun calculateResult() {
        try {
            val (result, isError) = evaluateExpression(expression)
            binding.resultTextView.text = result

            val textColor = if (isError) Color.RED else Color.BLACK
            binding.resultTextView.setTextColor(textColor)
            binding.expressionTextView.setTextColor(textColor)
        } catch (e: Exception) {
            binding.resultTextView.text = getString(R.string.error)
            binding.resultTextView.setTextColor(Color.RED)
            binding.expressionTextView.setTextColor(Color.RED)
        }
    }
}
