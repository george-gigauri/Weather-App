package ge.george.openweather.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.george.openweather.data.model.weather.Weather
import ge.george.openweather.data.network.Resource
import ge.george.openweather.data.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val openWeatherRepository: OpenWeatherRepository
) : ViewModel() {

    val getWeather: MutableLiveData<Resource<Weather?>> = MutableLiveData()

    fun getWeatherData(city: String) = viewModelScope.launch {
        openWeatherRepository.getCurrentWeather(city).collect {
            getWeather.value = it
        }
    }
}