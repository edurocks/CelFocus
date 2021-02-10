package com.example.celfocus.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.celfocus.databinding.WeatherDateRowBinding
import com.example.celfocus.model.WeatherItem
import com.example.celfocus.model.Weather
import com.example.celfocus.utils.DateHelper
import java.util.*


class WeatherAdapter(private val weatherList: ArrayList<WeatherItem>)
    : RecyclerView.Adapter<WeatherAdapter.WeatherAvatarHolder>() {

    class WeatherAvatarHolder(private val itemBinding: WeatherDateRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(weatherInfo: WeatherItem, context: Context) {
            itemBinding.weatherDate.text = DateHelper.dateFormatter(weatherInfo.date)

            val weatherInfoList: ArrayList<Weather> = weatherInfo.weatherList

            val gridLayoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemBinding.weatherInfoList.setHasFixedSize(true)
            itemBinding.weatherInfoList.layoutManager = gridLayoutManager
            val itemListDataAdapter = WeatherInfoAdapter(weatherInfoList)
            itemBinding.weatherInfoList.adapter = itemListDataAdapter

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAvatarHolder {
        val itemBinding = WeatherDateRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherAvatarHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WeatherAvatarHolder, position: Int) {
        val weatherInfo = weatherList[position]
        holder.bind(weatherInfo, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}