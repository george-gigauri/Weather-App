package ge.george.openweather.util.error

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    enum class NetworkType {
        TYPE_WIFI,
        TYPE_MOBILE,
        TYPE_NOT_CONNECTED,
        NETWORK_STATUS_NOT_CONNECTED,
        NETWORK_STATUS_WIFI,
        NETWORK_STATUS_MOBILE
    }

    private fun getConnectivityStatus(context: Context): NetworkType {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return NetworkType.TYPE_WIFI
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return NetworkType.TYPE_MOBILE
        }
        return NetworkType.TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusString(context: Context): NetworkType {
        val conn = getConnectivityStatus(context)
        var status = NetworkType.TYPE_NOT_CONNECTED
        when (conn) {
            NetworkType.TYPE_WIFI -> {
                status = NetworkType.NETWORK_STATUS_WIFI
            }
            NetworkType.TYPE_MOBILE -> {
                status = NetworkType.NETWORK_STATUS_MOBILE
            }
            NetworkType.TYPE_NOT_CONNECTED -> {
                status = NetworkType.NETWORK_STATUS_NOT_CONNECTED
            }
        }
        return status
    }
}