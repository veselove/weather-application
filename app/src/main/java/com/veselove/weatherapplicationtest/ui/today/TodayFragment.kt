package com.veselove.weatherapplicationtest.ui.today

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.veselove.weatherapplicationtest.*
import com.veselove.weatherapplicationtest.databinding.FragmentTodayBinding
import com.veselove.weatherapplicationtest.pojo.ForecastModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodayFragment : Fragment(), WeatherContract.View {

    private var _binding: FragmentTodayBinding? = null
    private lateinit var presenter: WeatherContract.Presenter
    private lateinit var model: WeatherContract.Model

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var shareForecast = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        model = WeatherModel()
        presenter = WeatherPresenter(this, model, Schedulers.io(), AndroidSchedulers.mainThread())
        presenter.init()
        presenter.getWeatherData()
        //imageView = findViewById(R.id.imageView)
        //button = findViewById(R.id.button)

        binding.btnShare.setOnClickListener { shareForecast() }

        return root
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        location = GPSLocation(context)
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

    override fun showWeatherData(forecastModel: ForecastModel) {

        Log.i("tempLog", "first fragment works")

        binding.tvLocation.text = forecastModel.location

        binding.ivWeatherMain.setImageResource(forecastModel.weather[0].weatherIcon)

        binding.tvTemperatureAndWeatherDescription.text = getString(R.string.temperature,
            forecastModel.weather[0].temperature, forecastModel.weatherDescription)

        binding.tvHumidity.text = getString(R.string.humidity, forecastModel.humidity)

        binding.tvRainVolume.text = getString(R.string.rain_volume, forecastModel.rainVolume)

        binding.tvPressureGroundLevel.text = getString(R.string.pressure_ground, forecastModel.pressureGroundLevel)

        binding.tvWindSpeed.text = getString(R.string.wind_speed, forecastModel.windSpeed)

        binding.tvWindDirection.text = forecastModel.windDirection

        binding.btnShare.visibility = View.VISIBLE

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

    override fun showErrorMessage(errorMsg: String?) {
        Snackbar.make(requireActivity().findViewById(R.id.container), errorMsg.toString(), Snackbar.LENGTH_INDEFINITE).apply {
            setAction("Try again") {
                presenter.getWeatherData()
            }
            show()
        }
    }

    override fun finish() {
        TODO("Not yet implemented")
    }
}