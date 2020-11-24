package ge.george.openweather.ui.adapter.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ge.george.openweather.R
import ge.george.openweather.data.model.GroupForecast
import ge.george.openweather.databinding.ForecastItemBinding
import ge.george.openweather.extension.intoWeekDay
import ge.george.openweather.extension.isVisible
import ge.george.openweather.util.TimeUtil

class WeekDayAdapter (
    private val context: Context,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<WeekDayAdapter.VH>() {
    private val arr: ArrayList<GroupForecast> = ArrayList()
    private lateinit var adapter: ForecastAdapter

    inner class VH(private val binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(i: GroupForecast) {
            binding.apply {
                weekDay.text = i.date.intoWeekDay()

                adapter = ForecastAdapter(context, fragmentManager)
                hourlyTemperature.adapter = adapter
                adapter.addAll(i.list)

                if (i.date.intoWeekDay() == TimeUtil.getCurrentWeekDay())
                    isCurrentDay.isVisible(true)
                else
                    isCurrentDay.isVisible(false)
            }
        }
    }

    fun add(weather: GroupForecast) {
        arr.add(weather);
        notifyDataSetChanged()
    }

    fun addAll(weathers: List<GroupForecast>) {
        arr.addAll(weathers);
        notifyDataSetChanged()
    }

    fun clear() {
        arr.clear();
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH (
        ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val i = arr[position]

        holder.bind(i)

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.recyclerview_fade))
    }

    override fun getItemCount(): Int = arr.size
}