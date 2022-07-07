package com.example.weather.model

import com.example.weather.domain.Weather

fun interface RepositorySingle {
    fun getWeather(lat: Double, lon: Double): Weather
}

fun interface RepositoryMulti {
    fun getListWeather(location: Location): List<Weather>
}

sealed class Location {
    object Russian : Location()
    object World : Location()
}
