package com.example.celfocus.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.celfocus.R
import com.example.celfocus.constants.Constants
import com.example.celfocus.databinding.FragmentWeatherBinding
import com.example.celfocus.model.Weather
import com.example.celfocus.repository.Resource
import com.example.celfocus.ui.adapter.WeatherAdapter
import com.example.celfocus.ui.viewModel.WeatherViewModel
import com.example.celfocus.utils.DateHelper
import com.example.celfocus.utils.LineChartValuesFormatter
import com.example.celfocus.utils.NetworkUtils
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherAdapter : WeatherAdapter
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (NetworkUtils.isInternetAvailable(requireContext())) {
            initRecyclerView()
            viewModel.getWeather(
                    getString(R.string.city_name),
                    getString(R.string.app_key),
                    getString(R.string.units)
            )
            observeResult()
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.weatherList.setHasFixedSize(true)
        binding.weatherList.layoutManager = linearLayoutManager
    }

    private fun observeResult() {
        viewModel.weatherFinalValue.observe(viewLifecycleOwner, { weatherResponse ->
            when (weatherResponse.status) {
                Resource.Status.SUCCESS -> {
                    weatherResponse.data?.let {
                        setCurrentWeather(it)
                        buildLineGraph(it)
                        set5DaysForecast(it)
                    }
                }

                Resource.Status.ERROR -> Log.e("Error", "Error")
                else -> Log.e("Failure", "Failure")
            }
        })

        viewModel.weatherList.observe(viewLifecycleOwner, { weatherItem ->

            weatherItem.sortWith { lhs, rhs -> lhs!!.date.compareTo(rhs!!.date) }

            weatherItem?.let {
                weatherAdapter = WeatherAdapter(it)
                binding.weatherList.adapter = weatherAdapter
            }
        })
    }

    private fun buildLineGraph(it: List<Weather>) {
        val data = it.filter { DateHelper.getCurrentDate() == it.dateTime.substring(0, 10) }

        val entries = arrayListOf<Entry>()
        data.forEachIndexed { index, weather ->
            entries.add(Entry(index.toFloat(), weather.main.temp.toFloat()))
        }

       val dataSet = LineDataSet(entries, getString(R.string.chart_label))
       dataSet.setDrawValues(false)
       dataSet.setCircleColor(Color.BLUE)
       dataSet.setDrawCircleHole(false)
       dataSet.lineWidth = 2f
       dataSet.color = Color.BLUE
       dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

       val lineData = LineData(dataSet)

       binding.chart.xAxis.setDrawGridLines(false)
       binding.chart.axisLeft.setDrawGridLines(true)
       binding.chart.axisRight.setDrawGridLines(true)
       binding.chart.axisLeft.gridColor = Color.GRAY
       binding.chart.axisRight.gridColor = Color.GRAY

       binding.chart.xAxis.setDrawLabels(false)
       binding.chart.axisLeft.setDrawLabels(false)

       binding.chart.xAxis.setDrawAxisLine(false)
       binding.chart.axisLeft.setDrawAxisLine(false)
       binding.chart.axisRight.setDrawAxisLine(false)

       val legend: Legend = binding.chart.legend
       legend.form = LegendForm.CIRCLE
       legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP

       binding.chart.description.isEnabled = false
       val right: YAxis = binding.chart.axisRight
       right.textColor = Color.BLUE
       right.typeface = Typeface.DEFAULT_BOLD
       right.valueFormatter = LineChartValuesFormatter()

       binding.chart.data = lineData
       binding.chart.invalidate()
    }

    private fun setCurrentWeather(it: List<Weather>) {
        Picasso.get().load(Constants.ICON_PREFIX + it[0].weather[0].icon + Constants.ICON_SUFFIX)
                            .into(binding.weatherIcon)
        binding.weatherTemp.text = String.format("%.1f\u00B0", it[0].main.temp)
    }

    private fun set5DaysForecast(it: List<Weather>) {
        val data = it.filter { DateHelper.getCurrentDate() < it.dateTime.substring(0, 10) }
        viewModel.groupDataIntoHashMap(data)
    }
}