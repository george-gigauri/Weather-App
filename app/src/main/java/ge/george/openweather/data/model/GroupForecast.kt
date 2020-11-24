package ge.george.openweather.data.model

import ge.george.openweather.data.model.forecast.MainList
import ge.george.openweather.data.model.weather.Weather

data class GroupForecast (
    val date: String,
    val list: List<MainList>
)