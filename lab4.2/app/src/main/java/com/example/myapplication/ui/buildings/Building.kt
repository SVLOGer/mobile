package com.example.app.models

data class Building(
    val name: String,
    val type: BuildingType, // Тип строения
    val count: Int, // Количество купленных строений
    val cost: Int, // Стоимость следующего строения
    val income: Double, // Заработок печенек
    val isAvailable: Boolean, // Доступность для покупки
)

enum class BuildingType {
    CLICKER, GRANDMA, FARM, MINE, FABRIC, BANK, TEMPLE, WIZARD_TOWER, ROCKET
}
