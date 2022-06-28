package com.example.weather.ui.view.details

import com.example.weather.domain.Weather

//интерфейс, чтобы передавать данные между адаптером списка и фрагментом:
interface OnItemViewClickListener {
    fun onItemViewClick(weather: Weather)
}