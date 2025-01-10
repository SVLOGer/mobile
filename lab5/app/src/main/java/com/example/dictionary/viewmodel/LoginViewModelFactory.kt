package com.example.dictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.data.SettingStorage

class LoginViewModelFactory(private val settingStorage: SettingStorage) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(settingStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}