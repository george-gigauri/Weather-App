package ge.george.openweather

import java.util.*

object TimeUtil {
    fun getCurrentHour() : Int = Calendar.getInstance()[Calendar.HOUR_OF_DAY]

    fun getCurrentDayState() : String = when (getCurrentHour()) {
        in 5..8 -> "sunrise"
        in 8..11 -> "morning"
        in 11..16 -> "midday"
        in 16..22 -> "sunset"
        else -> "night"
    }

    fun getBackground() : String? = Constants.backgrounds[getCurrentDayState()]
}