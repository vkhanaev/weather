package com.example.weather.model

import com.example.weather.domain.Weather
import com.example.weather.domain.getRussianCities
import com.example.weather.domain.getWorldCities

class RepositoryLocalImpl : RepositorySingle, RepositoryMulti {
    override fun getWeather(lat: Double, lon: Double) = Weather()

    override fun getListWeather(location: Location) = when (location) {
        is Location.Russian -> getRussianCities()
        is Location.World -> getWorldCities()
    }
}
