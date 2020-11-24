package ge.george.openweather.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
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

    fun intoWeekDay(time: String) : String {
        val c = Calendar.getInstance()
        val date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).parse(time)
        c.time = date!!

        return when (c[Calendar.DAY_OF_WEEK]) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "Sunday"
        }
    }

    fun getCurrentWeekDay() : String {
        val c = Calendar.getInstance()
        return when (c[Calendar.DAY_OF_WEEK]) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "Sunday"
        }
    }

    fun intoTime(time: Long) : String {
        val calendar = Calendar.getInstance()
        //val date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).parse(time)
        calendar.timeInMillis = time * 1000L
        return "${calendar[Calendar.HOUR_OF_DAY]}:${Calendar.MINUTE}"
    }
}