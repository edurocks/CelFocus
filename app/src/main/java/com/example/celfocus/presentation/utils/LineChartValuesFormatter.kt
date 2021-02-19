package com.example.celfocus.presentation.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class LineChartValuesFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return String.format("%.1f\u00B0C", value)
    }
}