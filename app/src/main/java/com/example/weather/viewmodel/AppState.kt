package com.example.weather.viewmodel

import com.example.weather.domain.Weather

sealed class AppState {
    data class SuccessSingle(val weatherData: Weather) : AppState()  // возвращает данные о погоде для одного города
    data class SuccessMulti(val weatherData: List<Weather>) : AppState()  // возвращает данные о погоде для списка городов
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
