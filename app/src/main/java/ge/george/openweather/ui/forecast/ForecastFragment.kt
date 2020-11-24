package ge.george.openweather.ui.forecast

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import coil.load
import ge.george.openweather.R
import ge.george.openweather.databinding.ForecastFragmentBinding
import ge.george.openweather.extension.isVisible
import ge.george.openweather.ui.MainActivity
import ge.george.openweather.ui.adapter.weather.WeekDayAdapter
import ge.george.openweather.util.LocationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        isLoaded = false
        MainActivity.binding.progress.isVisible(true)
        LocationUtil._city.observe(viewLifecycleOwner) { city->
            GlobalScope.launch(Dispatchers.IO) {
                val data = viewModel.getForecast(city)

                if (data != null && !isLoaded) {
                    GlobalScope.launch(Dispatchers.Main) {
                        adapter.clear()
                        adapter.addAll(data)
                        binding.weekDaysRv.post {
                            MainActivity.binding.progress.isVisible(false)
                            binding.refreshLayout.isRefreshing = false
                            isLoaded = true
                        }
                    }
                }
            }
        }
    }
}