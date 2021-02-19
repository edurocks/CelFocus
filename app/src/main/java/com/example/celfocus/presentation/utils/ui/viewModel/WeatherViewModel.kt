package com.example.celfocus.presentation.utils.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.celfocus.data.model.WeatherItem
import com.example.celfocus.data.repository.Resource
import com.example.celfocus.domain.entities.WeatherEntity
import com.example.celfocus.domain.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val useCase: GetWeatherUseCase)
                                                                            : ViewModel() {

    private var _weatherValue : MutableLiveData<Resource<List<WeatherEntity>>> = MutableLiveData()
    private var mLearningsData : ArrayList<WeatherItem> = ArrayList()
    private var _weatherList : MutableLiveData<ArrayList<WeatherItem>> = MutableLiveData()

    val weatherList : LiveData<ArrayList<WeatherItem>> get() {
        return _weatherList
    }

    val weatherFinalValue : LiveData<Resource<List<WeatherEntity>>>
        get() {
            return _weatherValue
        }

    fun getWeather(city : String, appKey : String, units : String){
        viewModelScope.launch {
            _weatherValue.value = useCase.invoke(city, appKey, units)
        }
    }

    fun groupDataIntoHashMap(weathers: List<WeatherEntity>) {
        val groupedWeatherData: HashMap<String, ArrayList<WeatherEntity>> = HashMap()
        for (weather in weathers) {
            val dateInitial: String = weather.dateTime.substring(0, 10)
            if (groupedWeatherData.containsKey(dateInitial)) {
                groupedWeatherData[dateInitial]?.add(weather)
            } else {
                val weatherFinal: ArrayList<WeatherEntity> = ArrayList()
                weatherFinal.add(weather)
                groupedWeatherData[dateInitial] = weatherFinal
            }
        }
        
        getFinalWeatherList(groupedWeatherData)
    }

   private fun getFinalWeatherList(groupedWeatherData: HashMap<String, ArrayList<WeatherEntity>>) {
        for (date in groupedWeatherData.keys) {
            val dateItem = WeatherItem()
            dateItem.date = date
            val weatherData: ArrayList<WeatherEntity> = ArrayList(groupedWeatherData[date])
            dateItem.weatherList = weatherData
            mLearningsData.add(dateItem)
            _weatherList.value = mLearningsData
        }
    }
}