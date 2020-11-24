package ge.george.openweather.data.model.weather


import com.google.gson.annotations.SerializedName

data class Wind(
    val deg: Int,
    val speed: Double
)