package com.example.weather.domain

// Создали модель данных
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
)

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 20,
    val feelsLike: Int = 21
)

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)



