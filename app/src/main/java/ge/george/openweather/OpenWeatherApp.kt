package ge.george.openweather

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import ge.george.openweather.ui.ErrorActivity
import ge.george.openweather.util.LocationUtil
import ge.george.openweather.util.error.Error
import ge.george.openweather.util.error.NetworkUtil
import java.util.*

@HiltAndroidApp
class OpenWeatherApp : Application() {

    private lateinit var locationUtil: LocationUtil

    override fun onCreate() {
        super.onCreate()

        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]

        if (hour in 8..19) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Toast.makeText(applicationContext, "Day, So day mode", Toast.LENGTH_SHORT).show()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Toast.makeText(applicationContext, "Sorry, Dark Mode cuz its night :(", Toast.LENGTH_SHORT).show()
        }

        if (NetworkUtil.getConnectivityStatusString(this) == NetworkUtil.NetworkType.NETWORK_STATUS_NOT_CONNECTED) {
            val intent = Intent(applicationContext, ErrorActivity::class.java)
            intent.putExtra("error_type", Error.TYPE_NETWORK)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        locationUtil = LocationUtil(this)
    }
}