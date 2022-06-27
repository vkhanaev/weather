package com.example.weather.ui.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.Repository
import com.example.weather.model.RepositoryLocalImpl
import com.example.weather.model.RepositoryRemoteImpl
import com.example.weather.viewmodel.AppState
import java.lang.IllegalStateException
import java.lang.Thread.sleep
import kotlin.random.Random

// Добавили в конструктор реализацию liveData
class MainViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {

    //добавили репозиторий
    lateinit var repository: Repository

    //Добавили из урока
    private fun choiceRepository(){
        repository = if(isConnection()) {
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }
    }

    // Для передачи liveDataToObserve в observe
    fun getLiveData() : MutableLiveData<AppState> {
        choiceRepository()
        return liveDataToObserve
    }
    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()  // Добавили данные из удаленного источника, но пока не реализовано

    // В классе LiveData доступны методы setValue и postValue: первый метод для обновления данных из основного потока, второй — из рабочего потока.
    private fun getDataFromLocalSource() {

        // Перед отправкой «асинхронного запроса» состояние приложения меняется на Loading
        liveDataToObserve.value = AppState.Loading

        // Эмулируем ошибку
        /*
        Thread {
            sleep(300L)
            // Если данные передаются в основном потоке, используем метод setValue. Отправляем данные из репозитория
            liveDataToObserve.postValue(AppState.Success(repository.getWeather(55.755826, 37.617299900000035)))
        }.start()
        */

        if(Random.nextInt(0,3) == 2) {
            liveDataToObserve.postValue(AppState.Error(IllegalStateException("что-то пошло не так")))
        }else{
            liveDataToObserve.postValue(AppState.Success(repository.getWeather(55.755826, 37.617299900000035)))
        }
    }

    // добавили из урока
    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()

    }

}
