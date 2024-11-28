package com.example.myapplication.ui

import com.example.app.models.Building

data class GameState(
    val cookieCount: Double = 0.0, // Текущее количество печенек
    val cookiesPerSecond: Double = 0.0,
    val buildings: List<Building> = emptyList(), // Список зданий
    val averageSpeed: Double = 0.0,
    val elapsedTime: String = "0:00"
)
