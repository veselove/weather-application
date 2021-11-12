package com.veselove.weatherapplicationtest.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veselove.weatherapplicationtest.MainContract
import com.veselove.weatherapplicationtest.WeatherPresenter
import com.veselove.weatherapplicationtest.databinding.FragmentTodayBinding

class TodayFragment : Fragment(), MainContract.View {

    private var _binding: FragmentTodayBinding? = null
    internal lateinit var presenter: MainContract.Presenter

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

        //imageView = findViewById(R.id.imageView)
        //button = findViewById(R.id.button)
        init()

        return root
    }

    private fun init() {
        this.presenter = WeatherPresenter(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayWeatherState() {
        TODO("Not yet implemented")
    }
}