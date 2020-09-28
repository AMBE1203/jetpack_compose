package com.example.jetcalendar.model

import com.example.jetcalendar.data.january2020
import com.example.jetcalendar.data.year2020

data class DaySelected(val day: Int, val month: CalendarMonth) {
    val calendarDay = lazy { month.getDay(day = day) }
    override fun toString(): String {
        return "${month.name.substring(0, 3).capitalize()} $day"
    }

    operator fun compareTo(other: DaySelected): Int {
        if (day == other.day && month == other.month) return 0
        if (month == other.month) return day.compareTo(other.day)
        return (year2020.indexOf(month).compareTo(year2020.indexOf(other.month)))
    }
}

val DaySelectedEmpty = DaySelected(-1, january2020)
