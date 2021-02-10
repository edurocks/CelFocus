package com.example.celfocus.utils

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    companion object {

        fun getCurrentDate() : String{
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.format(Date())
        }

        fun dateFormatter(date: String): String {
            val inputFormat = "yyyy-MM-dd"
            val outputFormat = "E, MMMM d"
            val parsed: Date?
            var outputDate : String
            try {
                val df_input = SimpleDateFormat(inputFormat, Locale.getDefault())
                val df_output = SimpleDateFormat(outputFormat, Locale.getDefault())
                parsed = df_input.parse(date)
                outputDate = df_output.format(parsed)
            } catch (e: Exception) {
                outputDate = date
            }
            return outputDate
        }
    }
}