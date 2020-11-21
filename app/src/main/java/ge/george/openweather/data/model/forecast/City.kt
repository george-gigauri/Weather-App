package ge.george.openweather.data.model.forecast


import com.google.gson.annotations.SerializedName

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String
)