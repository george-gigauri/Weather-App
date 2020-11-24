package ge.george.openweather.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ge.george.openweather.data.model.weather.Weather
import ge.george.openweather.data.repository.OpenWeatherRepository
import kotlinx.coroutines.runBlocking

class MainViewModel @ViewModelInject constructor(
    private val openWeatherRepository: OpenWeatherRepository) : ViewModel() {
    private val getWeather: MutableLiveData<Weather> = MutableLiveData()

    fun getWeatherData(city: String) : LiveData<Weather> {
        getWeather.value = runBlocking { openWeatherRepository.getCurrentWeather(city) }
        return getWeather
    }
}