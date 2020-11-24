package ge.george.openweather.ui.adapter.weather

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ge.george.openweather.R
import ge.george.openweather.data.model.forecast.MainList
import ge.george.openweather.databinding.ForecastItemBinding
import ge.george.openweather.databinding.TemperatureItemBinding
import ge.george.openweather.extension.intoTime
import ge.george.openweather.extension.intoWeekDay
import ge.george.openweather.ui.bottom.BottomSheetDetails
import ge.george.openweather.ui.main.MainFragment

class ForecastAdapter (
    private val context: Context,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ForecastAdapter.VH>() {
    private val arr = ArrayList<MainList>()

    inner class VH (private val binding: TemperatureItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(i: MainList) {
            binding.apply {
                time.text = i.dt.intoTime()
                temperature.text = "${i.main.temp}°C"
                weatherTitle.text = i.weather[0].description
                weatherIcon.load(i.weather[0].iconPng)

                root.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("avg_temp", "${i.main.temp}°C")
                    bundle.putString("min_temp", "${i.main.tempMin}°C")
                    bundle.putString("max_temp", "${i.main.tempMax}°C")
                    bundle.putString("pressure", "${i.main.pressure}")
                    bundle.putString("humidity", "${i.main.humidity}%")
                    bundle.putString("wind_speed", "${i.wind.speed} m/s")

                    val bottomSheet = BottomSheetDetails()
                    bottomSheet.arguments = bundle
                    bottomSheet.show(fragmentManager, bottomSheet.tag)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH (
        TemperatureItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val i = arr[position]

        holder.bind(i)

        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.recyclerview_fade)
    }

    override fun getItemCount(): Int = arr.size

    fun add(mainList: MainList) {
        arr.add(mainList)
        notifyDataSetChanged()
    }

    fun addAll(mainList: List<MainList>) {
        arr.addAll(mainList)
        notifyDataSetChanged()
    }

    fun clear() {
        arr.clear()
        notifyDataSetChanged()
    }
}