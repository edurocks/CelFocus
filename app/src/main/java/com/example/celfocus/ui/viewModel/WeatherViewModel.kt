package com.example.celfocus.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.celfocus.model.WeatherItem
import com.example.celfocus.model.Weather
import com.example.celfocus.repository.Resource
import com.example.celfocus.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository)
                        : ViewModel() {

    private var _weatherValue : MutableLiveData<Resource<List<Weather>>> = MutableLiveData()
    private var mLearningsData : ArrayList<WeatherItem> = ArrayList()
    private var _weatherList : MutableLiveData<ArrayList<WeatherItem>> = MutableLiveData()

    val weatherList : LiveData<ArrayList<WeatherItem>> get() {
        return _weatherList
    }

    val weatherFinalValue : LiveData<Resource<List<Weather>>>
        get() {
            return _weatherValue
        }

    fun getWeather(city : String, appKey : String, units : String){
        viewModelScope.launch {
            _weatherValue.value = weatherRepository.getWeatherForecast(city, appKey, units)
        }
    }

    fun groupDataIntoHashMap(weathers: List<Weather>) {
        val groupedWeatherData: HashMap<String, ArrayList<Weather>> = HashMap()
        for (weather in weathers) {
            val dateInitial: String = weather.dateTime.substring(0, 10)
            if (groupedWeatherData.containsKey(dateInitial)) {
                groupedWeatherData[dateInitial]?.add(weather)
            } else {
                val weatherFinal: ArrayList<Weather> = ArrayList()
                weatherFinal.add(weather)
                groupedWeatherData[dateInitial] = weatherFinal
            }
        }
        
        getFinalWeatherList(groupedWeatherData)
    }

   private fun getFinalWeatherList(groupedWeatherData: HashMap<String, ArrayList<Weather>>) {
        for (date in groupedWeatherData.keys) {
            val dateItem = WeatherItem()
            dateItem.date = date
            val weatherData: ArrayList<Weather> = ArrayList(groupedWeatherData[date])
            dateItem.weatherList = weatherData
            mLearningsData.add(dateItem)
            _weatherList.value = mLearningsData
        }
    }
}