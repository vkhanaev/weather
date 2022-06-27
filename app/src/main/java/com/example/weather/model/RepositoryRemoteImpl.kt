package com.example.weather.model

import com.example.weather.domain.Weather

class RepositoryRemoteImpl : Repository {
    override fun getListWeather(): List<Weather> {
        Thread{
            Thread.sleep(200L)
        }.start()
        return listOf(Weather())
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }

}