package ge.george.openweather.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import ge.george.openweather.R
import ge.george.openweather.util.TimeUtil
import ge.george.openweather.databinding.MainFragmentBinding
import ge.george.openweather.extension.isVisible
import ge.george.openweather.extension.toast
import ge.george.openweather.ui.MainActivity
import ge.george.openweather.util.LocationUtil
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.bind(view)

        val backgroundImage = TimeUtil.getBackground()
        if (backgroundImage != null)
            binding.backgroundImage.load(backgroundImage)

        init()
    }

    private fun init() {
        load()

        binding.liveDotIcon.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.blink)
        binding.refresher.setOnRefreshListener {
            if (binding.refresher.isRefreshing) {
                load()
            }
        }

        binding.cityName.setOnClickListener {
            val intent = Intent (
                ACTION_VIEW,
                Uri.parse("geo:${LocationUtil.latitude},${LocationUtil.longitude}")
            )
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun load() {
        MainActivity.binding.progress.isVisible(true)
        LocationUtil._city.observe(viewLifecycleOwner) { cityy->
            viewModel.getWeatherData(cityy).observe(viewLifecycleOwner) {
                binding.apply {
                    cityName.text = it.name
                    weatherTitle.text = it.weather[0].main
                    currentTemperature.text = "${it.main.temp}°C"
                    weatherIcon.load(it.weather[0].iconPng)

                    minTemperature.text = "${it.main.tempMin}°C"
                    maxTemperature.text = "${it.main.tempMax}°C"
                    humidityLevel.text = "${it.main.humidity}%"
                    windSpeed.text = "${it.wind.speed} m/s"

                    val dateFormat = SimpleDateFormat("hh:mm aa");
                    val dt =  Date(it.dt * 1000L)
                    refreshTime.text = dateFormat.format(dt)
                }

                MainActivity.binding.progress.isVisible(false)
                binding.refresher.isRefreshing = false

                loadAnimations()
            }
        }
    }

    private fun loadAnimations() {
        val fade = AnimationUtils.loadAnimation(requireContext(), R.anim.recyclerview_fade)
        val bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        val blink =AnimationUtils.loadAnimation(requireContext(), R.anim.blink)

        binding.liveDotIcon.startAnimation(blink)
        binding.locationIcon.startAnimation(bounce)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val backgroundImage = TimeUtil.getBackground()
        if (backgroundImage != null)
            binding.backgroundImage.load(backgroundImage)
    }
}