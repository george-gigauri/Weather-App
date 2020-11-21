package ge.george.openweather.data.model.forecast


import com.google.gson.annotations.SerializedName

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<>,
    val message: Double
)