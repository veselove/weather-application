package com.veselove.weatherapplicationtest.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veselove.weatherapplicationtest.*
import com.veselove.weatherapplicationtest.databinding.FragmentTodayBinding
import com.veselove.weatherapplicationtest.pojo.ForecastModel
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

    override fun showWeatherData(forecastModel: ForecastModel) {

        binding.tvLocation.text = forecastModel.location

        binding.ivWeatherMain.setImageResource(forecastModel.weather[0].weatherIcon)

        binding.tvTemperature.text = getString(R.string.temperature, forecastModel.weather[0].temperature)

        binding.tvWeatherMain.text = forecastModel.weatherDescription

        binding.tvHumidity.text = getString(R.string.humidity, forecastModel.humidity)

        binding.tvRainVolume.text = getString(R.string.rain_volume, forecastModel.rainVolume)

        binding.tvPressureGroundLevel.text = getString(R.string.pressure_ground, forecastModel.pressureGroundLevel)

        binding.tvWindSpeed.text = getString(R.string.wind_speed, forecastModel.windSpeed)

        binding.tvWindDirection.text = forecastModel.windDirection

    }

    override fun showErrorMessage(errorMsg: String?) {
        TODO("Not yet implemented")
    }

    override fun finish() {
        TODO("Not yet implemented")
    }
}