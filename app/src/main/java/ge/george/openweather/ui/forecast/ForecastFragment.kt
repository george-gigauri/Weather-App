package ge.george.openweather.ui.forecast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ge.george.openweather.R
import ge.george.openweather.data.network.Resource
import ge.george.openweather.databinding.ForecastFragmentBinding
import ge.george.openweather.extension.isVisible
import ge.george.openweather.extension.toast
import ge.george.openweather.ui.MainActivity
import ge.george.openweather.ui.adapter.weather.WeekDayAdapter
import ge.george.openweather.util.LocationUtil

class ForecastFragment : Fragment(R.layout.forecast_fragment) {
    private lateinit var binding: ForecastFragmentBinding
    private lateinit var viewModel: ForecastFragmentViewModel
    private lateinit var adapter: WeekDayAdapter
    private var isLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ForecastFragmentBinding.bind(view)

        val currentMode = AppCompatDelegate.getDefaultNightMode()
        if (currentMode == AppCompatDelegate.MODE_NIGHT_NO)
            binding.headerImageBackground.load("https://i.pinimg.com/originals/94/a7/ea/94a7ead2109c809c05419ec659323200.jpg")
        else
            binding.headerImageBackground.load("https://c4.wallpaperflare.com/wallpaper/737/338/350/night-cityscape-colorful-new-york-city-wallpaper-preview.jpg")

        adapter = WeekDayAdapter(requireContext(), requireActivity().supportFragmentManager)
        binding.weekDaysRv.adapter = adapter

        load()

        binding.refreshLayout.setOnRefreshListener {
            load()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ForecastFragmentViewModel::class.java]
    }

    private fun load() {
        MainActivity.binding.progress.isVisible(true)
        LocationUtil._city.observe(viewLifecycleOwner) { city ->
            viewModel.getForecast(city)

            viewModel.forecast.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val data = it.data!!

                        adapter.clear()
                        adapter.addAll(data)

                        binding.weekDaysRv.post {
                            MainActivity.binding.progress.isVisible(false)
                            binding.refreshLayout.isRefreshing = false
                        }
                    }

                    Resource.Status.ERROR -> {
                        toast(it.message!!)
                    }

                    Resource.Status.LOADING -> {
                        MainActivity.binding.progress.isVisible(true)
                        binding.refreshLayout.isRefreshing = true
                    }
                }
            }
        }
    }
}