package com.example.weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

/*
class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
}
*/

// Добавили в конструктор реализацию liveData
class MainViewModel(val liveDataToObserve: MutableLiveData<Any> =
                        MutableLiveData()) : ViewModel() {

    // Для передачи liveDataToObserve в observe
    fun getData(): LiveData<Any> {
        getDataFromLocalSource()
        return liveDataToObserve
    }

    // В классе LiveData доступны методы setValue и postValue: первый метод для обновления данных из основного потока, второй — из рабочего потока.
    private fun getDataFromLocalSource() {
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(Any())  // Если данные передаются в основном потоке, используем метод setValue. Т
        }.start()
    }
}
