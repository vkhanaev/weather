package com.example.weather.ui.view.weatherlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.*
import com.example.weather.viewmodel.AppState
import java.lang.IllegalStateException
import java.lang.Thread.sleep
import kotlin.random.Random

class WeatherListViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {

    private lateinit var repositorySingle: RepositorySingle
    private lateinit var repositoryMulti: RepositoryMulti

    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveDataToObserve
    }

    private fun choiceRepository() {
        repositorySingle = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
        repositoryMulti = RepositoryLocalImpl()
    }

    fun getWeatherListForRussia() = getDataFromLocalSource(Location.Russian)
    fun getWeatherListForWorld() = getDataFromLocalSource(Location.World)

    private fun getDataFromLocalSource(location: Location) {
        liveDataToObserve.value = AppState.Loading

        Thread {
            //sleep(300L)
            //if (Random.nextInt(0, 10) == 1) {
            if (false) {
                liveDataToObserve.postValue(AppState.Error(IllegalStateException("что-то пошло не так")))
            } else {
                liveDataToObserve.postValue(
                    AppState.SuccessMulti(
                        repositoryMulti.getListWeather(
                            location
                        )
                    )
                )
            }
        }.start()

    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()

    }
}
