package com.example.weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.viewmodel.AppState
import java.lang.Thread.sleep

// Добавили в конструктор реализацию liveData
class MainViewModel(val liveDataToObserve: MutableLiveData<AppState> =
                        MutableLiveData()) : ViewModel() {

    // Для передачи liveDataToObserve в observe
    fun getLiveData() = liveDataToObserve
    fun getWeather() = getDataFromLocalSource()

    // В классе LiveData доступны методы setValue и postValue: первый метод для обновления данных из основного потока, второй — из рабочего потока.
    private fun getDataFromLocalSource() {

        // Перед отправкой «асинхронного запроса» состояние приложения меняется на Loading
        liveDataToObserve.value = AppState.Loading

        Thread {
            sleep(300L)
            liveDataToObserve.postValue(AppState.Success(Any()))  // Если данные передаются в основном потоке, используем метод setValue. Т
        }.start()
    }
}
