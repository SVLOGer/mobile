package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.models.Building
import com.example.myapplication.R
import com.example.myapplication.ui.clicker.ClickerUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    // Состояние для клика
    private val _clickerUiState = MutableStateFlow(ClickerUiState())
    val clickerUiState: StateFlow<ClickerUiState> = _clickerUiState

    // Состояние для зданий
    private val _buildings = MutableStateFlow<List<Building>>(emptyList())
    val buildings: StateFlow<List<Building>> get() = _buildings

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

                _clickerUiState.value = _clickerUiState.value.copy(
                    cookieCount = _clickerUiState.value.cookieCount + _clickerUiState.value.cookiesPerSecond
                )
            }
        }
    }

    fun onCookieClicked() {
        _clickerUiState.value = _clickerUiState.value.copy(
            cookieCount = _clickerUiState.value.cookieCount + 1.0
        )
    }

    fun reduceCookies(amount: Int) {
        if (_clickerUiState.value.cookieCount >= amount) {
            _clickerUiState.value = _clickerUiState.value.copy(
                cookieCount = _clickerUiState.value.cookieCount - amount
            )
        }
    }

    fun increaseIncome(amount: Double) {
        _clickerUiState.value = _clickerUiState.value.copy(
            cookiesPerSecond = _clickerUiState.value.cookiesPerSecond + amount
        )
        _clickerUiState.value = _clickerUiState.value.copy(
            averageSpeed = _clickerUiState.value.cookiesPerSecond * 60.0
        )
    }

    // --- Методы для зданий ---
    private fun initializeBuildings() {
        _buildings.value = listOf(
            Building("Клик", R.drawable.item_background, 0, 15, 0.1, false),
            Building("Бабуля", R.drawable.item_background, 0, 100, 1.0, false),
            Building("Ферма", R.drawable.item_background, 0, 1100, 8.0, false),
            Building("Шахта", R.drawable.item_background, 0, 12000, 47.0, false),
            Building("Фабрика", R.drawable.item_background, 0, 130000, 260.0, false),
            Building("Банк", R.drawable.item_background, 0, 1400000, 1400.0, false),
            Building("Храм", R.drawable.item_background, 0, 20000000, 7800.0, false),
            Building("Башня волшебника", R.drawable.item_background, 0, 330000000, 44000.0, false),
            Building("Космический корабль", R.drawable.item_background, 0, 510000000, 260000.0, false)
        )
    }

    fun buyBuilding(building: Building): Boolean {
        val currentCookies = clickerUiState.value.cookieCount
        return if (currentCookies >= building.cost) {
            // Успешная покупка
            reduceCookies(building.cost)
            increaseIncome(building.income)
            val updatedBuildings = _buildings.value.map { build ->
                if (building.name == build.name) {
                    build.copy(count = build.count + 1, cost = (build.cost * 1.15).toInt())
                } else {
                    build
                }
            }
            _buildings.value = updatedBuildings

            viewModelScope.launch {
                _toastMessage.emit("Вы купили \"${building.name}\"!")
            }
            true
        } else {
            // Недостаточно печенек
            viewModelScope.launch {
                _toastMessage.emit("Недостаточно печенек для покупки \"${building.name}\"")
            }
            false
        }
    }

    fun updateBuildingsAvailability() {
        val currentCookies = _clickerUiState.value.cookieCount
        val updatedBuildings = _buildings.value.map { building ->
            building.copy(isAvailable = currentCookies >= building.cost)
        }
        _buildings.value = updatedBuildings
    }
}
