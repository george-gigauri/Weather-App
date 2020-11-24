package ge.george.openweather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ge.george.openweather.R
import ge.george.openweather.databinding.ActivityMainBinding
import ge.george.openweather.ui.adapter.ViewPagerAdapter
import ge.george.openweather.util.LocationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var binding: ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_OpenWeather)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            ), 101)
        } else init()
    }

    private fun init() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
            val titles = arrayOf("Today", "Forecast")
            TabLayoutMediator(binding.tabMenu, binding.viewPager) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }.start()

        val orientation = resources.configuration.orientation
        if (orientation == ORIENTATION_LANDSCAPE)
        {
            configTabMenu(false)
        } else {
            configTabMenu()
        }
    }

    private fun configTabMenu(isPortrait: Boolean = true) {
        val tabParams = binding.tabMenu.layoutParams
        val pagerParams = binding.viewPager.layoutParams

        if (!isPortrait) {
            tabParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
            binding.tabMenu.layoutParams = tabParams

            pagerParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            binding.viewPager.layoutParams = pagerParams
        } else {
            tabParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            binding.tabMenu.layoutParams = tabParams

            pagerParams.height = 0
            binding.viewPager.layoutParams = pagerParams
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val orientation = resources.configuration.orientation
        if (orientation == ORIENTATION_LANDSCAPE)
        {
            binding.tabMenu.setBackgroundColor(Color.TRANSPARENT)
            configTabMenu(false)
        } else {
            configTabMenu()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if (grantResults.isNotEmpty())
                if (grantResults[0] == PERMISSION_GRANTED)
                    init()
                else
                    exitProcess(0)
            else
                exitProcess(0)
        }
    }
}