package com.example.weather.ui.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.*
import com.example.weather.viewmodel.AppState
import java.lang.IllegalStateException
import java.lang.Thread.sleep
import kotlin.random.Random

// Добавили в конструктор реализацию liveData
class MainViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {

    //разделили репозиторий на два
    lateinit var repositorySingle: RepositorySingle
    lateinit var repositoryMulti: RepositoryMulti

    // Для передачи liveDataToObserve в observe
    fun getLiveData() : MutableLiveData<AppState> {
        choiceRepository()
        return liveDataToObserve
    }

    //Добавили из урока
    private fun choiceRepository(){
        repositorySingle = if(isConnection()) {
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }
        repositoryMulti = RepositoryLocalImpl()
    }

    // разбили функцию получения данных о погоде на две - по локациям
    fun getWeatherListFromLocalSourceRussia() = getDataFromLocalSource(Location.Russian)
    fun getWeatherListFromLocalSourceWorld() = getDataFromLocalSource(Location.World)

    fun getWeatherFromRemoteSourceRussia() = getDataFromLocalSource(Location.Russian)  // Добавили данные из удаленного источника, но пока не реализовано
    fun getWeatherFromRemoteSourceWorld() = getDataFromLocalSource(Location.World)  // Добавили данные из удаленного источника, но пока не реализовано

    // В классе LiveData доступны методы setValue и postValue: первый метод для обновления данных из основного потока, второй — из рабочего потока.
    private fun getDataFromLocalSource(location: Location) {  // теперь возвращаем данные для списка городов в зависимости от Location

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
            liveDataToObserve.postValue(AppState.SuccessMulti(repositoryMulti.getListWeather(location)))
        }
    }

    fun getWeather() {
        liveDataToObserve.value = AppState.Loading
        if(Random.nextInt(0,3) == 2) {
            liveDataToObserve.postValue(AppState.Error(IllegalStateException("что-то пошло не так")))
        }else{
            liveDataToObserve.postValue(AppState.SuccessSingle(repositorySingle.getWeather(55.755826, 37.617299900000035)))
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
