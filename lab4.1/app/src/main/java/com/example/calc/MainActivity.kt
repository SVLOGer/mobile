package com.example.calc

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.calc.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpButtons()
        observeState()
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
            binding.btnEqual.id -> viewModel.onEvent(CalculatorEvent.Calculate)
            binding.btnDelete.id -> viewModel.onEvent(CalculatorEvent.DeleteLast)
            else -> viewModel.onEvent(CalculatorEvent.EnterDigit(button.text.toString()))
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.expressionTextView.text = state.expression
                binding.resultTextView.text = state.result

                val textColor = if (state.isError) Color.RED else Color.BLACK
                binding.resultTextView.setTextColor(textColor)
                binding.expressionTextView.setTextColor(textColor)
            }
        }
    }
}
