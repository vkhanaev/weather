package com.example.weather.model

import com.example.weather.domain.Weather

//Сделали интерфейс репозитория функциональным (чтобы дальше можно было бы использовать лямбда) - разбили интерфейс на несколько одинарных
fun interface RepositorySingle {
    fun getWeather(lat: Double, lon: Double): Weather  // добавили из урока
}

fun interface RepositoryMulti {
    fun getListWeather(location: Location): List<Weather>  // в зависимости то location получим погоду из различных источников
}

/* из методички
interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorageRus(): Weather
    fun getWeatherFromLocalStorageWorld(): Weather
}
*/

// Создали sealed-класс для обозначения состояния Location
sealed class Location {
    object Russian:Location()
    object World:Location()
}

