package com.example.weather.ui.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.android.synthetic.main.main_fragment.*

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    private val viewModel: WeatherListViewModel by lazy {
        ViewModelProvider(this).get(WeatherListViewModel::class.java)
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = WeatherListAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(
                        R.id.container,
                        DetailsFragment.newInstance(Bundle().apply {
                            putParcelable(
                                DetailsFragment.BUNDLE_EXTRA,
                                weather
                            )
                        })
                    )
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private var location: Location = Location.World

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })

        viewModel.getWeatherListForRussia()
    }

    private fun changeWeatherDataSet() =
        if (location == Location.Russian) {
            viewModel.getWeatherListForWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
            //location = Location.World
        } else {
            viewModel.getWeatherListForRussia()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessMulti -> {
                val weatherData = appState.weatherData
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)

                if (location == Location.Russian) {
                    location = Location.World
                } else {
                    location = Location.Russian
                }
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE

                when (location) {
                    is Location.Russian -> {
                        weatherListFragmentRootView.showSnackBar(
                            getString(R.string.error),
                            getString(R.string.reload),
                            { viewModel.getWeatherListForWorld() })
                    }
                    is Location.World -> {
                        weatherListFragmentRootView.showSnackBar(
                            getString(R.string.error),
                            getString(R.string.reload),
                            { viewModel.getWeatherListForRussia() })
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        adapter.removeListener()
        super.onDestroyView()
        _binding = null
    }

}

private fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}
