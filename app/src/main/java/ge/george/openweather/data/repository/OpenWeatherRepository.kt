package ge.george.openweather.data.repository

import ge.george.openweather.data.model.GroupForecast
import ge.george.openweather.data.model.weather.Weather
import ge.george.openweather.data.network.OpenWeatherAPI
import ge.george.openweather.data.network.Resource
import ge.george.openweather.extension.intoWeekDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherRepository @Inject constructor(private val openWeatherAPI: OpenWeatherAPI) {

    suspend fun getCurrentWeather(q: String): Flow<Resource<Weather?>> = flow {
        emit(Resource.loading<Weather>())
        val response = openWeatherAPI.getWeather(q)

        if (!response.isSuccessful)
            emit(Resource.error<Weather>(response.message()))
        else if (response.body() == null)
            emit(Resource.error<Weather>("Response does not have any body!"))
        else
            emit(Resource.success(response.body()))

    }.flowOn(Dispatchers.IO)

    suspend fun getForecast(q: String) = flow {
        emit(Resource.loading())

        val response = openWeatherAPI.getForecast(q)
        val body = response.body()
        val result = ArrayList<GroupForecast>()

        body?.list?.forEach { list ->
            val temp = body.list.filter { it.dtTxt.intoWeekDay() == list.dtTxt.intoWeekDay() }

            val forecast = GroupForecast(list.dtTxt, temp)
            result.add(forecast)
        }

        if (!response.isSuccessful || body == null)
            emit(Resource.error<List<GroupForecast>>(response.message()))
        else emit(Resource.success(result.distinctBy { it.date.intoWeekDay() }))

    }.flowOn(Dispatchers.IO)
}