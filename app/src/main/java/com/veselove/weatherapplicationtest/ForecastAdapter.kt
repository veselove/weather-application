package com.veselove.weatherapplicationtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.veselove.weatherapplicationtest.pojo.ForecastModel
import com.veselove.weatherapplicationtest.pojo.ForecastUnit

class ForecastAdapter constructor(private val forecastModel: ForecastModel) : RecyclerView.Adapter<ForecastAdapter.ForecastHolder>() {

    class ForecastHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvDayOfWeek: TextView = view.findViewById(R.id.tvDayOfWeek)
        private val ivWeather3h: ImageView = view.findViewById(R.id.ivWeather3h)
        private val tvTime3h: TextView = view.findViewById(R.id.tvTime3h)
        private val tvWeatherDescription3h: TextView = view.findViewById(R.id.tvWeatherDescription3h)
        private val tvTemperature3h: TextView = view.findViewById(R.id.tvTemperature3h)

        fun setForecastData(forecastUnit: ForecastUnit) {
            tvDayOfWeek.text = forecastUnit.dayOfWeek
            ivWeather3h.setImageResource(forecastUnit.weatherIcon)
            tvTime3h.text = forecastUnit.time
            tvWeatherDescription3h.text = forecastUnit.weatherDescription
            tvTemperature3h.text = forecastUnit.temperature
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_TODAY
        } else {
            if (forecastModel.weather[position].time == "0:00" ||
                forecastModel.weather[position].time == "1:00" ||
                forecastModel.weather[position].time == "2:00") {
                return TYPE_NEW_DAY
            } else {
                if (forecastModel.weather[position].time == "21:00" ||
                    forecastModel.weather[position].time == "22:00" ||
                    forecastModel.weather[position].time == "23:00") {
                    return TYPE_LAST
                } else return TYPE_REGULAR
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        val layout = when (viewType) {
            TYPE_REGULAR -> R.layout.forecast_rv_item
            TYPE_TODAY -> R.layout.forecast_rv_item_today
            TYPE_NEW_DAY -> R.layout.forecast_rv_item_new_day
            TYPE_LAST -> R.layout.forecast_rv_item_last
            else -> R.layout.forecast_rv_item
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ForecastHolder(view)
    }


    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        return holder.setForecastData(forecastModel.weather[position])
    }

    override fun getItemCount(): Int {
        return forecastModel.weather.size
    }

    companion object {
        private const val TYPE_REGULAR = 0
        private const val TYPE_TODAY = 1
        private const val TYPE_NEW_DAY = 2
        private const val TYPE_LAST = 3
    }

}