package com.example.app.models

data class Building(
    val name: String,
    val icon: Int, // ID ресурса для изображения
    val count: Int, // Количество купленных строений
    val cost: Int, // Стоимость следующего строения
    val income: Double, // Заработок печенек
    val isAvailable: Boolean, // Доступность для покупки
)
