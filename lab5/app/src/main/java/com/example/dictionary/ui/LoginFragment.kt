package com.example.dictionary.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.data.SettingStorage
import com.example.dictionary.databinding.FragmentLoginBinding
import com.example.dictionary.viewmodel.LoginState
import com.example.dictionary.viewmodel.LoginViewModel
import com.example.dictionary.viewmodel.LoginViewModelFactory
import com.example.dictionary.viewmodel.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by activityViewModels {
        LoginViewModelFactory(SettingStorage(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        lifecycleScope.launch {
            viewModel.loadActualPincode()
        }

        viewModel.state
            .onEach { loginState ->
                updatePincodeView(loginState)

                if ((loginState.currentState == State.ENTER_PIN || loginState.currentState == State.REPEAT_PIN) && loginState.pincode.length == 4 && !loginState.isError) {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentDiary, null, navOptions)
                }

                if (loginState.isError) {
                    Toast.makeText(requireContext(), "Неверный PIN-код", Toast.LENGTH_SHORT).show()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        val buttons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                viewModel.appendToPincode(button.text.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePincodeView(loginState: LoginState) {
        when (loginState.currentState) {
            State.ENTER_PIN -> binding.title.text = "Введите PIN-код"
            State.CREATE_PIN -> binding.title.text = "Придумайте PIN-код"
            State.REPEAT_PIN -> binding.title.text = "Повторите PIN-код"
        }

        val pinLength = loginState.pincode.length
        binding.circle1.setBackgroundResource(if (pinLength > 0) R.drawable.filled_round_pin_code else R.drawable.round_pin_code)
        binding.circle2.setBackgroundResource(if (pinLength > 1) R.drawable.filled_round_pin_code else R.drawable.round_pin_code)
        binding.circle3.setBackgroundResource(if (pinLength > 2) R.drawable.filled_round_pin_code else R.drawable.round_pin_code)
        binding.circle4.setBackgroundResource(if (pinLength > 3) R.drawable.filled_round_pin_code else R.drawable.round_pin_code)
    }
}