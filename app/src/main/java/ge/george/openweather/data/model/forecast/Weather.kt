package ge.george.openweather.data.model.forecast

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) {
    val iconPng: String get() = "http://openweathermap.org/img/w/$icon.png"
}