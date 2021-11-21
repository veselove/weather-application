package com.veselove.weatherapplicationtest.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.veselove.weatherapplicationtest.*
import com.veselove.weatherapplicationtest.databinding.FragmentTodayBinding
import com.veselove.weatherapplicationtest.pojo.ForecastModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodayFragment : Fragment(), WeatherContract.View {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: WeatherContract.Presenter
    private lateinit var model: WeatherContract.Model

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root
        model = WeatherModel()
        presenter = WeatherPresenter(this, model, Schedulers.io(), AndroidSchedulers.mainThread())
        presenter.getWeatherData()
        binding.btnShare.setOnClickListener { shareForecast() }
        return root
    }

    override fun showWeatherData(forecastModel: ForecastModel) {
        binding.tvLocation.text = forecastModel.location
        binding.ivWeatherMain.setImageResource(forecastModel.weather[0].weatherIcon)
        binding.tvTemperatureAndWeatherDescription.text = getString(R.string.today_temperature,
            forecastModel.weather[0].temperature, forecastModel.weatherDescription)
        binding.tvHumidity.text = getString(R.string.today_humidity, forecastModel.humidity)
        binding.tvRainVolume.text = getString(R.string.today_rain_volume, forecastModel.rainVolume)
        binding.tvPressureGroundLevel.text = getString(R.string.today_pressure_ground, forecastModel.pressureGroundLevel)
        binding.tvWindSpeed.text = getString(R.string.today_wind_speed, forecastModel.windSpeed)
        binding.tvWindDirection.text = forecastModel.windDirection
        binding.btnShare.visibility = View.VISIBLE
    }


    override fun showErrorMessage(errorMsg: String?) {
        binding.ivWeatherMain.setImageResource(R.mipmap.error)
        Snackbar.make(requireActivity().findViewById(R.id.container),
            requireContext().resources.getString(R.string.error_message),
            Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.error_try_again_button) {
                presenter.getWeatherData()
            }
            show()
        }
    }

    private fun shareForecast() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, presenter.shareForecast())
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }

}