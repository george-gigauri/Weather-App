package ge.george.openweather.ui.forecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ge.george.openweather.data.model.GroupForecast
import ge.george.openweather.data.repository.OpenWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ForecastFragmentViewModel @ViewModelInject constructor(
    private val openWeatherRepository: OpenWeatherRepository) : ViewModel() {
    private val forecast: MutableLiveData<List<GroupForecast>> = MutableLiveData()

    suspend fun getForecast(city: String) : List<GroupForecast>? = withContext(Dispatchers.IO) {
        return@withContext openWeatherRepository.getForecast(q = city)
    }
}