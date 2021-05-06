package ge.george.openweather.ui.forecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.george.openweather.data.model.GroupForecast
import ge.george.openweather.data.network.Resource
import ge.george.openweather.data.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ForecastFragmentViewModel @ViewModelInject constructor(
    private val openWeatherRepository: OpenWeatherRepository) : ViewModel() {

    val forecast: MutableLiveData<Resource<List<GroupForecast>>> = MutableLiveData()

    fun getForecast(city: String) = viewModelScope.launch {
        openWeatherRepository.getForecast(q = city).collect {
            forecast.value = it
        }
    }
}