package com.example.weather.viewmodel

import com.example.weather.domain.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()  // Возвращаем данные о погоде
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
