package ge.george.openweather

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

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