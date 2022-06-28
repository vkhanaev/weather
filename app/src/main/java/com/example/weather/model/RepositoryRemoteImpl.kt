package com.example.weather.model

import com.example.weather.domain.Weather
import com.example.weather.domain.getRussianCities
import com.example.weather.domain.getWorldCities

class RepositoryRemoteImpl : RepositorySingle, RepositoryMulti {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }

    override fun getListWeather(location: Location): List<Weather> {
        Thread{
            Thread.sleep(200L)
        }.start()
        //return listOf(Weather())
        // в зависимости от location отдаем разные списки городов
        return when(location) {
            is Location.Russian -> getRussianCities()
            is Location.World -> getWorldCities()
        }
    }

}