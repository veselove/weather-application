package com.veselove.weatherapplicationtest.ui.today

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veselove.weatherapplicationtest.*
import com.veselove.weatherapplicationtest.databinding.FragmentTodayBinding
import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodayFragment : Fragment(), WeatherContract.View {

    private var _binding: FragmentTodayBinding? = null
    lateinit var presenter: WeatherContract.Presenter
    lateinit var model: WeatherContract.Model

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        model = WeatherModel()
        presenter = WeatherPresenter(this, model, Schedulers.io(), AndroidSchedulers.mainThread())
        presenter.init()
        presenter.getWeatherData()
        //imageView = findViewById(R.id.imageView)
        //button = findViewById(R.id.button)

        return root
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        this.presenter = WeatherPresenter(context)
//    }
//
//    private fun init() {
//        this.presenter = WeatherPresenter(activity.getActivity())
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }

    override fun onInitView() {
    }

    override fun handleLoaderView(isLoading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showWeatherData(weatherResponse: WeatherResponse) {
        when (weatherResponse.list[0].weather[0].description) {
            "clear sky" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.clear_sky_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.clear_sky_day)
            "few clouds" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.few_clouds_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.few_clouds_day)
            "scattered clouds" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.scattered_clouds_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.scattered_clouds_day)
            "broken clouds" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.broken_clouds_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.broken_clouds_day)
            "shower rain" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.shower_rain_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.shower_rain_day)
            "rain" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.rain_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.rain_day)
            "thunderstorm" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.thunderstorm_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.thunderstorm_day)
            "snow" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.snow_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.snow_day)
            "mist" -> if (weatherResponse.list[0].sys.pod == "n")
                binding.ivWeatherMain.setImageResource(R.mipmap.mist_night) else
                binding.ivWeatherMain.setImageResource(R.mipmap.mist_day)
            else -> binding.ivWeatherMain.setImageResource(R.mipmap.error)
        }

        binding.tvTemperature.text =
            getString(R.string.temperature, weatherResponse.list[0].main.temp.toString())

        binding.tvWeatherMain.text = weatherResponse.list[0].weather[0].main

        binding.tvHumidity.text =
            getString(R.string.humidity, weatherResponse.list[0].main.humidity)

        if (weatherResponse.list[0].weather[0].main == "Rain") {
                binding.tvRainVolume.text =
                    getString(R.string.rain_volume, weatherResponse.list[0].rain.`3h`.toString())
        } else {
            binding.tvRainVolume.text =
                getString(R.string.rain_volume, "0.0")
        }

        binding.tvPressureGroundLevel.text =
            getString(R.string.pressure_ground, weatherResponse.list[0].main.grnd_level)

        binding.tvWindSpeed.text =
            getString(R.string.wind_speed, weatherResponse.list[0].wind.speed.toInt())
        when (weatherResponse.list[0].wind.deg) {
            in 0..22 -> binding.tvWindDirection.text = "N"
            in 23..67 -> binding.tvWindDirection.text = "NE"
            in 68..112 -> binding.tvWindDirection.text = "E"
            in 113..157 -> binding.tvWindDirection.text = "SE"
            in 158..202 -> binding.tvWindDirection.text = "S"
            in 203..247 -> binding.tvWindDirection.text = "SW"
            in 248..292 -> binding.tvWindDirection.text = "W"
            in 293..337 -> binding.tvWindDirection.text ="NW"
            in 338..360 -> binding.tvWindDirection.text ="N"
        }
    }

    override fun showErrorMessage(errorMsg: String?) {
        TODO("Not yet implemented")
    }

    override fun setLocation(location: String?) {
        binding.tvCityName.text = location
    }

    override fun finish() {
        TODO("Not yet implemented")
    }
}