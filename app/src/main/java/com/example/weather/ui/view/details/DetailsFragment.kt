package com.example.weather.ui.view.details

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.weather.BuildConfig
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailsBinding
import com.example.weather.domain.Weather
import com.example.weather.model.dto.WeatherDTO
import com.example.weather.utils.WeatherLoader
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailsFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
        object : WeatherLoader.WeatherLoaderListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                displayWeather(weatherDTO)
            }

            override fun onFailed(throwable: Throwable) {
                //Обработка ошибки
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        val loader = WeatherLoader(
            onLoadListener, weatherBundle.city.lat,
            weatherBundle.city.lon
        )
        loader.loadWeather()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            val city = weatherBundle.city
            cityName.text = city.name
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            weatherCondition.text = translateCondition(weatherDTO.fact?.condition)
            temperatureValue.text = weatherDTO.fact?.temp.toString()
            feelsLikeValue.text = weatherDTO.fact?.feels_like.toString()
        }
    }

    //Код расшифровки погодного описания. Возможные значения:
    fun translateCondition(condition: String?) =
        when (condition) {
                "clear" -> "ясно"
                "partly-cloudy" -> "малооблачно"
                "cloudy" -> "облачно с прояснениями"
                "overcast" -> "пасмурно"
                "drizzle" -> "морось"
                "light-rain" -> "небольшой дождь"
                "rain" -> "дождь"
                "moderate-rain" -> "умеренно сильный дождь"
                "heavy-rain" -> "сильный дождь"
                "continuous-heavy-rain" -> "длительный сильный дождь"
                "showers" -> "ливень"
                "wet-snow" -> "дождь со снегом"
                "light-snow" -> "небольшой снег"
                "snow" -> "снег"
                "snow-showers" -> "снегопад"
                "hail" -> "град"
                "thunderstorm" -> "гроза"
                "thunderstorm-with-rain" -> "дождь с грозой"
                "thunderstorm-with-hail" -> "гроза с градом"
                else -> "unknown"
            }.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}
