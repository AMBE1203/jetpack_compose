package com.example.jetcharts.ui.pie

import androidx.compose.ui.graphics.Color

data class PieChartData(val slices: List<Slice>) {

    internal val totalSize: Float
        get() {

            return slices.map { it.value }.sum()
        }

    data class Slice(val value: Float, val color: Color)
}