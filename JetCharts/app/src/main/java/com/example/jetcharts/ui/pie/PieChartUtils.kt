package com.example.jetcharts.ui.pie

internal object PieChartUtils {
    fun calculateAngle(
        sliceLength: Float,
        totalLength: Float,
        progress: Float
    ): Float = 360.0f * (sliceLength * progress) / totalLength
}