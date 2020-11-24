package ge.george.openweather.data.network

import ge.george.openweather.BuildConfig
import ge.george.openweather.data.model.forecast.Forecast
import ge.george.openweather.data.model.weather.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @GET("weather?appid=$API_KEY")
    suspend fun getWeather (
        @Query("q") q: String,
        @Query("units") metrics: String = "metric"
    ) : Response<Weather>

    @GET("forecast?appid=$API_KEY")
    suspend fun getForecast(
        @Query("q") q: String,
        @Query("units") units: String = "metric"
    ) : Response<Forecast>
}