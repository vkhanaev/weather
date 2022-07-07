package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.databinding.MainActivityBinding
import com.example.weather.ui.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView((binding.getRoot()))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance())
                .commitNow()
        }
    }
}
