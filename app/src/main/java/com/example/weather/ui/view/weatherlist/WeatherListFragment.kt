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
import com.example.weather.ui.view.details.DetailsFragment
import com.example.weather.ui.view.details.OnItemViewClickListener
import com.example.weather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    private lateinit var viewModel: WeatherListViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = WeatherListAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                manager.beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private var location : Location = Location.Russian

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         binding.mainFragmentRecyclerView.adapter = adapter
         binding.mainFragmentFAB.setOnClickListener {changeWeatherDataSet()}

         viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
         viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })

        viewModel.getWeatherListForRussia()
    }

    private fun changeWeatherDataSet() {
        if (location == Location.Russian) {
            viewModel.getWeatherListForWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
            location = Location.World
        } else {
            viewModel.getWeatherListForRussia()
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
            .setAction(getString(R.string.reload)) { viewModel.getWeatherListForRussia()}
            .show()
    }

    override fun onDestroyView() {
        adapter.removeListener()
        super.onDestroyView()
        _binding = null
    }

}