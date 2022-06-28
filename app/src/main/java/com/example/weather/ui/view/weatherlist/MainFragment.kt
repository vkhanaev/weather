package com.example.weather.ui.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.databinding.MainFragmentBinding
import com.example.weather.domain.Weather
import com.example.weather.model.Location
import com.example.weather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    // Добавили adapter
    private val adapter = MainFragmentAdapter()
    //private var isDataSetRus: Boolean = true
    private var location : Location = Location.Russian


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

     // Заменили onActivityCreated на onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         // Подключили adapter для наполнения RecyclerView
         binding.mainFragmentRecyclerView.adapter = adapter
         binding.mainFragmentFAB.setOnClickListener {changeWeatherDataSet()}  // добавили listener на FAB

         viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
         viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }

        })

        // Отправляем запрос на получение данных
        viewModel.getWeatherListFromLocalSourceRussia()
    }

    // меняем location и загружаем список городов для location
    private fun changeWeatherDataSet() {
        if (location == Location.Russian) {
            viewModel.getWeatherListFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
            location = Location.World
        } else {
            viewModel.getWeatherListFromLocalSourceRussia()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            location = Location.Russian
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessMulti-> {
                val weatherData = appState.weatherData
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val error = appState.error
                setError(error)

            }
        }

    }

    private fun setError(error: Throwable) {
        Snackbar
            .make(binding.mainFragmentFAB, "Ошибка $error", Snackbar.LENGTH_INDEFINITE)
            //.setAction("Reload") { viewModel.getWeather() }
            .setAction(getString(R.string.reload)) { viewModel.getWeatherListFromLocalSourceRussia()}

            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}