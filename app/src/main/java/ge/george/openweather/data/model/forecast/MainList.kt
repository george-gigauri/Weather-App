package ge.george.openweather.data.model.forecast


import com.google.gson.annotations.SerializedName

data class (
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val rain: Rain,
    val snow: Snow,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)