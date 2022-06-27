package com.example.weather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.MainFragmentBinding
import com.example.weather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Заменил на binding
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
        //return inflater.inflate(R.layout.main_fragment, container, false)
    }

     // Заменили onActivityCreated на onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Подключили viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Добавили observer, что такое it?
        // Получили LiveData, вызвали observe
        val observer = Observer<AppState> { renderData(it) }
        val LiveData = viewModel.getLiveData()
        LiveData.observe(viewLifecycleOwner, observer)  // Теперь, если данные, которые хранит LiveData, изменятся, Observer сразу об этом узнает и вызовет метод renderData, куда передаст новые данные.
/*
        // Подписываемся, Any - тип объекта в liveDataToObserve
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState?) {
                Toast.makeText(context, "Работает $t", Toast.LENGTH_LONG).show()
            }

        })
*/
        // Отправляем запрос на получение данных
        viewModel.getWeather()
    }
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val weatherData = appState.weatherData
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeather() }
                    .show()
            }
        }

    }



}