package ge.george.openweather.data.model.weather

data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) {
    val iconPng: String get() = "http://openweathermap.org/img/w/$icon.png"
}