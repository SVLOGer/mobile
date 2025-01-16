package com.example.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class CategoryViewModel : ViewModel() {
    val categories: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf("Личные", "По работе", "Учебные"))
}