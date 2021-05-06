package ge.george.openweather.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import ge.george.openweather.R
import ge.george.openweather.data.network.Resource
import ge.george.openweather.databinding.MainFragmentBinding
import ge.george.openweather.extension.isVisible
import ge.george.openweather.extension.toast
import ge.george.openweather.ui.MainActivity
import ge.george.openweather.util.LocationUtil
import ge.george.openweather.util.TimeUtil
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

        viewModel.getWeather.observe(viewLifecycleOwner) {
            binding.apply {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        MainActivity.binding.progress.isVisible(false)
                        val data = it.data

                        cityName.text = data?.name
                        weatherTitle.text = data!!.weather[0].main
                        currentTemperature.text = "${data.main.temp}°C"
                        weatherIcon.load(data.weather[0].iconPng)

                        minTemperature.text = "${data.main.tempMin}°C"
                        maxTemperature.text = "${data.main.tempMax}°C"
                        humidityLevel.text = "${data.main.humidity}%"
                        windSpeed.text = "${data.wind.speed} m/s"

                        val dateFormat = SimpleDateFormat("hh:mm aa");
                        val dt = Date(data.dt * 1000L)
                        refreshTime.text = dateFormat.format(dt)
                    }

                    Resource.Status.ERROR -> {
                        MainActivity.binding.progress.isVisible(false)
                        toast(it.message.toString())
                    }

                    Resource.Status.LOADING -> {
                        MainActivity.binding.progress.isVisible(true)
                    }
                }
            }
        }
    }

    private fun init() {
        load()

        binding.liveDotIcon.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.blink)
        binding.refresher.setOnRefreshListener {
            if (binding.refresher.isRefreshing) {
                load()
            }
        }

        binding.cityName.setOnClickListener {
            val intent = Intent(
                ACTION_VIEW,
                Uri.parse("geo:${LocationUtil.latitude},${LocationUtil.longitude}")
            )
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun load() {
        LocationUtil._city.observe(viewLifecycleOwner) { cityy ->
            viewModel.getWeatherData(cityy)

            MainActivity.binding.progress.isVisible(false)
            binding.refresher.isRefreshing = false

            loadAnimations()
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