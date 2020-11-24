package ge.george.openweather.data.model.forecast

import com.google.gson.annotations.SerializedName

class Rain(
    @SerializedName("3h")
    val _3h: Double
)