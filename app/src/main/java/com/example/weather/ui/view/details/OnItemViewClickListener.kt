package com.example.weather.ui.view.details

import com.example.weather.domain.Weather

interface OnItemViewClickListener {
    fun onItemViewClick(weather: Weather)
}
