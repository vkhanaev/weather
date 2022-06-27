package com.example.weather.model

import com.example.weather.domain.Weather

//Создали интерфейс репозитория
interface Repository {
    fun getListWeather(): List<Weather>  // добавили из урока
    fun getWeather(lat: Double, lon: Double): Weather  // добавили из урока

    //fun getWeatherFromServer(): Weather
    //fun getWeatherFromLocalStorage(): Weather
}
