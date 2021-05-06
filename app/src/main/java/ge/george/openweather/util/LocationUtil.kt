package ge.george.openweather.util

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ge.george.openweather.ui.ErrorActivity
import ge.george.openweather.ui.MainActivity
import ge.george.openweather.util.error.Error
import java.util.*

class LocationUtil(
    private val context: Context
) : LocationListener {
    companion object {
        var latitude: Double? = null
        var longitude: Double? = null
        val _city: MutableLiveData<String> = MutableLiveData()
    }

    init {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, 0L, 0f, this)
        }
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude

        Log.i("Location", "lat:$latitude  lng:$longitude")

        getCity()
    }

    override fun onProviderEnabled(provider: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun onProviderDisabled(provider: String) {
        val intent = Intent(context, ErrorActivity::class.java)
        intent.putExtra("error_type", Error.TYPE_LOCATION)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun getCity() {
        if (Geocoder.isPresent()) {
            val geocoder = Geocoder(context, Locale.ENGLISH)

            if (latitude != null && longitude != null) {
                val addresses = geocoder.getFromLocation(latitude!!, longitude!!, 1)
                _city.value = addresses[0].adminArea
            }
        } else {
            throw Exception("Geocoder is not available. Are you running this app on emulator? I think so.")
        }
    }
}