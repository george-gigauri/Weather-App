package ge.george.openweather.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import ge.george.openweather.R
import ge.george.openweather.data.model.weather.Main
import ge.george.openweather.databinding.ActivityErrorBinding
import ge.george.openweather.extension.isVisible
import ge.george.openweather.util.error.Error
import ge.george.openweather.util.error.NetworkUtil

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_error)
        binding.lifecycleOwner = this

        val error = intent.extras?.getInt("error_type", Error.TYPE_NETWORK)

        binding.apply {
            errorImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.no_internet, null))
            errorMessageTitle.text = Error.TITLE_NO_CONNECTION
            errorMessage.text = Error.MESSAGE_NO_CONNECTION
            retry.isVisible(true)

            if (error != null) {
                if (error == Error.TYPE_LOCATION) {
                    errorImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_no_gps, null))
                    errorMessageTitle.text = Error.TITLE_NO_GPS
                    errorMessage.text = Error.MESSAGE_NO_GPS
                    retry.isVisible(false)
                }
            }

            retry.setOnClickListener {
                val status = NetworkUtil.getConnectivityStatusString(applicationContext)
                if (status == NetworkUtil.TYPE_WIFI ||
                        status == NetworkUtil.TYPE_MOBILE) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }
    }
}