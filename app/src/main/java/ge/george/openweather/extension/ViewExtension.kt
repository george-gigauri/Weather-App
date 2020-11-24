package ge.george.openweather.extension

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ge.george.openweather.util.TimeUtil

fun View.isVisible(isVisible: Boolean) {
    val v = if (isVisible) View.VISIBLE else View.GONE
    this.visibility = v
}

fun Activity.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

fun String.intoWeekDay(): String {
    return TimeUtil.intoWeekDay(this)
}

fun Int.intoTime(): String {
    return TimeUtil.intoTime(this.toLong())
}