package ge.george.openweather.data.model.forecast

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<MainList>,
    val message: Double
)