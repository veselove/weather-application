package com.veselove.weatherapplicationtest.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veselove.weatherapplicationtest.WeatherContract
import com.veselove.weatherapplicationtest.WeatherPresenter
import com.veselove.weatherapplicationtest.databinding.FragmentForecastBinding

//class ForecastFragment : Fragment(), WeatherContract.View {
//
//    private var _binding: FragmentForecastBinding? = null
//    internal lateinit var presenter: WeatherContract.Presenter
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentForecastBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        //imageView = findViewById(R.id.imageView)
//        //button = findViewById(R.id.button)
//        init()
//
//        return root
//    }
//
//    private fun init() {
//        //this.presenter = WeatherPresenter(this)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    override fun displayWeatherState() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onInitView() {
//        TODO("Not yet implemented")
//    }
//}