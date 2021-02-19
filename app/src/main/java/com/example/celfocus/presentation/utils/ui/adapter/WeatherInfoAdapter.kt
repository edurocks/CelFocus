package com.example.celfocus.presentation.utils.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.celfocus.constants.Constants
import com.example.celfocus.databinding.WeatherInfoRowBinding
import com.example.celfocus.data.model.Weather
import com.example.celfocus.domain.entities.WeatherEntity
import com.squareup.picasso.Picasso
import java.util.*

class WeatherInfoAdapter(private val weatherInfoList: ArrayList<WeatherEntity>)  :
        RecyclerView.Adapter<WeatherInfoAdapter.WeatherInfoAvatarHolder>() {

    class WeatherInfoAvatarHolder(private val itemBinding: WeatherInfoRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(weatherData: WeatherEntity) {
            Picasso.get().load(Constants.ICON_PREFIX + weatherData.weather[0].icon + Constants.ICON_SUFFIX)
                    .into(itemBinding.weatherImage)
            itemBinding.weatherTemp.text = String.format("%.0f\u00B0", weatherData.main.temp)
            itemBinding.weatherHour.text = weatherData.dateTime.substring(10, 13)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoAvatarHolder {
        val itemBinding = WeatherInfoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherInfoAvatarHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WeatherInfoAvatarHolder, position: Int) {
        val weatherData = weatherInfoList[position]
        holder.bind(weatherData)
    }

    override fun getItemCount(): Int {
        return weatherInfoList.size
    }
}