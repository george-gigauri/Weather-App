package ge.george.openweather.data.repository

import android.util.Log
import androidx.constraintlayout.widget.Group
import ge.george.openweather.data.model.GroupForecast
import ge.george.openweather.data.model.forecast.Forecast
import ge.george.openweather.data.model.forecast.MainList
import ge.george.openweather.data.model.weather.Weather
import ge.george.openweather.data.network.OpenWeatherAPI
import ge.george.openweather.extension.intoWeekDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherRepository @Inject constructor(private val openWeatherAPI: OpenWeatherAPI) {
    suspend fun getCurrentWeather(q: String) : Weather? = withContext(Dispatchers.IO) {
        val response = openWeatherAPI.getWeather(q)
        return@withContext response.body()
    }

    suspend fun getForecast(q: String) : List<GroupForecast>? = withContext(Dispatchers.IO) {
        val response = openWeatherAPI.getForecast(q)
        val body = response.body()
        val result = ArrayList<GroupForecast>()

        if (!response.isSuccessful || body == null)
            return@withContext emptyList()

        body.list.forEach {
            val temp = ArrayList<MainList>()

            for (i in body.list) {
                if (it.dtTxt.intoWeekDay() == i.dtTxt.intoWeekDay()) {
                    temp.add(i)
                }
            }

            val forecast = GroupForecast(it.dtTxt, temp)
            result.add(forecast)
        }

        Log.i("ArrayListResult", result.toString())
        return@withContext result.distinctBy { it.date.intoWeekDay() }
    }
}