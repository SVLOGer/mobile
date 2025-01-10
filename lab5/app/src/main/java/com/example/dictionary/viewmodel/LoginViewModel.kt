package com.example.dictionary.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.SettingStorage
import kotlinx.coroutines.launch

enum class State {
    CREATE_PIN,
    REPEAT_PIN,
    ENTER_PIN,
}

data class LoginState(
    val currentState: State,
    val pincode: String = "",
    val isError: Boolean = false
)

class LoginViewModel(private val settingStorage: SettingStorage) : ViewModel() {
    private val _state = MutableStateFlow(LoginState(State.CREATE_PIN))
    val state = _state.asStateFlow()

    var actualPincode: String? = null
        private set

    fun appendToPincode(value: String) {
        val newPincode = _state.value.pincode + value
        updateState(newPincode)
    }

    private fun updateState(newPincode: String) {
        val currentState = _state.value.currentState

        when (currentState) {
            State.ENTER_PIN -> {
                if (newPincode.length == 4) {
                    if (newPincode == actualPincode) {
                        _state.value = _state.value.copy(pincode = newPincode, isError = false)
                    } else {
                        _state.value = _state.value.copy(pincode = "", isError = true)
                    }
                } else {
                    _state.value = _state.value.copy(pincode = newPincode, isError = false)
                }
            }
            State.CREATE_PIN -> {
                if (newPincode.length == 4) {
                    actualPincode = newPincode
                    _state.value = _state.value.copy(pincode = "", currentState = State.REPEAT_PIN, isError = false)
                } else {
                    _state.value = _state.value.copy(pincode = newPincode, isError = false)
                }
            }
            State.REPEAT_PIN -> {
                if (newPincode.length == 4) {
                    if (newPincode == actualPincode) {
                        viewModelScope.launch {
                            settingStorage.savePin(newPincode)
                        }
                        _state.value = _state.value.copy(pincode = newPincode, isError = false)
                    } else {
                        _state.value = _state.value.copy(pincode = "", currentState = State.CREATE_PIN, isError = true)
                    }
                } else {
                    _state.value = _state.value.copy(pincode = newPincode, isError = false)
                }
            }
        }
    }

    suspend fun loadActualPincode() {
        actualPincode = settingStorage.getPin()
        _state.value = if (actualPincode.isNullOrBlank()) {
            LoginState(State.CREATE_PIN)
        } else {
            LoginState(State.ENTER_PIN)
        }
    }
}