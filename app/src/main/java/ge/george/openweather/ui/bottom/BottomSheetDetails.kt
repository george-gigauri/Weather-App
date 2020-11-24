package ge.george.openweather.ui.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ge.george.openweather.R
import ge.george.openweather.databinding.BottomSheetWeatherDetailsBinding

class BottomSheetDetails : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetWeatherDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_weather_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetWeatherDetailsBinding.bind(view)

        setValues()
    }

    private fun setValues() {
        val averageTemperature = arguments?.getString("avg_temp")
        val minimumTemperature = arguments?.getString("min_temp")
        val maximumTemperature = arguments?.getString("max_temp")
        val pressureLevel = arguments?.getString("pressure")
        val humidity = arguments?.getString("humidity")
        val windSpeed = arguments?.getString("wind_speed")

        binding.avgTemp.text = averageTemperature
        binding.minTemp.text = minimumTemperature
        binding.maxTemp.text = maximumTemperature
        binding.pressure.text = pressureLevel
        binding.humidity.text = humidity
        binding.windSpeed.text = windSpeed
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme
}