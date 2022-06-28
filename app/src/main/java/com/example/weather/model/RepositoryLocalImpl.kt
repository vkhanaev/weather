package com.example.weather.model

import com.example.weather.domain.Weather
import com.example.weather.domain.getRussianCities
import com.example.weather.domain.getWorldCities

class RepositoryLocalImpl : RepositorySingle, RepositoryMulti {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }

    override fun getListWeather(location: Location): List<Weather> {
        //return listOf(Weather())
        // в зависимости от location отдаем разные списки городов
        return when(location) {
            is Location.Russian -> getRussianCities()
            is Location.World -> getWorldCities()
        }
    }
}