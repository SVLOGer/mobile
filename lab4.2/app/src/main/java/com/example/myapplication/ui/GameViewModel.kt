package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.models.Building
import com.example.app.models.BuildingType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    // Состояние для клика
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage

    init {
        // Инициализация зданий и запуск производства печенек
        initializeBuildings()
        startCookieProduction()
    }

                // --- Методы для клика ---
    private fun startCookieProduction() {
        viewModelScope.launch {
            while (true) {
                delay(1000)

                _gameState.update {
                    it.copy(cookieCount = it.cookieCount + it.cookiesPerSecond)
                }

            }
        }
    }

    fun onCookieClicked() {
        _gameState.update {
            it.copy(cookieCount = it.cookieCount + 1.0)
        }
    }

    // --- Методы для зданий ---
    private fun initializeBuildings() {
        _gameState.value = _gameState.value.copy(
            buildings = listOf(
                Building("Клик", BuildingType.CLICKER, 0, 15, 0.1, false),
                Building("Бабуля", BuildingType.GRANDMA, 0, 100, 1.0, false),
                Building("Ферма", BuildingType.FARM, 0, 1100, 8.0, false),
                Building("Шахта", BuildingType.MINE, 0, 12000, 47.0, false),
                Building("Фабрика", BuildingType.FABRIC, 0, 130000, 260.0, false),
                Building("Банк", BuildingType.BANK, 0, 1400000, 1400.0, false),
                Building("Храм", BuildingType.TEMPLE, 0, 20000000, 7800.0, false),
                Building("Башня волшебника", BuildingType.WIZARD_TOWER, 0, 330000000, 44000.0, false),
                Building("Космический корабль", BuildingType.ROCKET, 0, 510000000, 260000.0, false)

            )
        )
    }

    fun buyBuilding(building: Building) {
        if (_gameState.value.cookieCount >= building.cost) {
            val updatedBuildings = _gameState.value.buildings.map {
                if (it.name == building.name) {
                    it.copy(
                        count = it.count + 1,
                        cost = (it.cost * 1.15).toInt()
                    )
                } else it
            }
            _gameState.value = _gameState.value.copy(
                cookieCount = _gameState.value.cookieCount - building.cost,
                buildings = updatedBuildings,
                cookiesPerSecond = _gameState.value.cookiesPerSecond + building.income
            )

            viewModelScope.launch {
                _toastMessage.emit("Вы купили ${building.name}!")
            }
        } else {
            viewModelScope.launch {
                _toastMessage.emit("Недостаточно печенек для покупки ${building.name}.")
            }
        }
    }

    fun updateBuildingsAvailability() {
        val currentCookies = _gameState.value.cookieCount
        val updatedBuildings = _gameState.value.buildings.map {
            it.copy(isAvailable = currentCookies >= it.cost)
        }
        _gameState.value = _gameState.value.copy(buildings = updatedBuildings)
    }
}
