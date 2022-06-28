package com.example.weather.ui.view.details

import com.example.weather.ui.view.MainViewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.databinding.FragmentDetailsBinding
import com.example.weather.domain.Weather
import com.example.weather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar


class DetailsFragment {
}


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Заменил на binding
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    // Заменили onActivityCreated на onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Подключили viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Подписываемся, Any - тип объекта в liveDataToObserve
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }

        })

        // Отправляем запрос на получение данных
        //viewModel.getWeatherFromRemoteSourceRussia()
        viewModel.getWeather()
    }
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessSingle -> {
                val weatherData = appState.weatherData
                binding.loadingLayout.visibility = View.GONE
                // покажем полученные данные
                setData(weatherData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                val error = appState.error
                setError(error)

            }
        }

    }

    private fun setData(weatherData: Weather) {
        binding.cityName.text = weatherData.city.name
        binding.temperatureValue.text = weatherData.temperature.toString()
        binding.feelsLikeValue.text = weatherData.feelsLike.toString()
        binding.cityCoordinates.text = "${weatherData.city.lat}/${weatherData.city.lon}"
    }

    private fun setError(error: Throwable) {
        Snackbar
            .make(binding.mainView, "Ошибка $error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Reload") { viewModel.getWeather() }
            .show()

        binding.cityName.text = "Unknown"
        binding.temperatureValue.text = "Unknown"
        binding.feelsLikeValue.text = "Unknown"
        binding.cityCoordinates.text = "Unknown"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}