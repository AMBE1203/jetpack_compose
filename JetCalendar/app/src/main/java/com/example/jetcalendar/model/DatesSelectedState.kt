package com.example.jetcalendar.model

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.jetcalendar.data.year2020

class DatesSelectedState {
    private var from by mutableStateOf(DaySelectedEmpty)
    private var to by mutableStateOf(DaySelectedEmpty)

    override fun toString(): String {
        if (from == DaySelectedEmpty && to == DaySelectedEmpty) return ""
        var output = from.toString()
        if (to != DaySelectedEmpty) {
            output += " - $to"
        }

        return output
    }

    fun daySelected(newDate: DaySelected) {
        if (from == DaySelectedEmpty && to == DaySelectedEmpty) {
            setDates(newDate, DaySelectedEmpty)
        } else if (from != DaySelectedEmpty && to != DaySelectedEmpty) {
            clearDates()
            from = DaySelectedEmpty
            to = DaySelectedEmpty
            daySelected(newDate = newDate)
        } else if (from == DaySelectedEmpty) {
            if (newDate < to) {
                setDates(newDate, to)
            } else if (newDate > to) {
                setDates(to, newDate)
            }
        } else if (to == DaySelectedEmpty) {
            if (newDate < from) {
                setDates(newDate, from)
            } else if (newDate > from) {
                setDates(from, newTo = newDate)
            }
        }
    }

    private fun setDates(newFrom: DaySelected, newTo: DaySelected) {
        if (newTo == DaySelectedEmpty) {
            from = newFrom
            from.calendarDay.value.status = DaySelectedStatus.FirstLastDay
        } else {
            from = newFrom.apply { calendarDay.value.status = DaySelectedStatus.FirstDay }
            selectDatesInBetween(newFrom, newTo)
            to = newTo.apply { calendarDay.value.status = DaySelectedStatus.LastDay }

        }
    }

    private fun selectDatesInBetween(from: DaySelected, to: DaySelected) {
        if (from.month == to.month) {
            for (i in (from.day + 1) until to.day) {
                from.month.getDay(i).status = DaySelectedStatus.Selected
            }
        } else {

            // fill from's month
            for (i in (from.day + 1) until from.month.numDays) {
                from.month.getDay(i).status = DaySelectedStatus.Selected
            }

            from.month.getDay(from.month.numDays).status = DaySelectedStatus.LastDay

            // fill in -between month
            for (i in (from.month.monthNumber + 1) until to.month.monthNumber) {
                val month = year2020[i - 1]
                month.getDay(1).status = DaySelectedStatus.FirstDay
                for (j in 2 until month.numDays) {
                    month.getDay(j).status = DaySelectedStatus.Selected
                }
                month.getDay(month.numDays).status = DaySelectedStatus.LastDay
            }

            // fill to's month
            to.month.getDay(1).status = DaySelectedStatus.FirstDay
            for (i in 2 until to.day) {
                to.month.getDay(i).status = DaySelectedStatus.Selected
            }
        }
    }

    @VisibleForTesting
    fun clearDates() {
        if (from != DaySelectedEmpty && to != DaySelectedEmpty) {
            // Unselect dates from the same month

            if (from.month == to.month) {
                for (i in from.day..to.day) {
                    from.month.getDay(i).status = DaySelectedStatus.NoSelected
                }
            } else {
                // Unselect from's month
                for (i in from.day..from.month.numDays) {
                    from.month.getDay(i).status = DaySelectedStatus.NoSelected
                }
                // fill in between month
                for (i in (from.month.monthNumber + 1) until to.month.monthNumber) {

                    val month = year2020[i - 1]
                    for (j in 1 until month.numDays) {
                        month.getDay(j).status = DaySelectedStatus.NoSelected
                    }
                }
                // fill to's month
                for (i in 1..to.day) {
                    to.month.getDay(i).status = DaySelectedStatus.NoSelected
                }

            }

        }
    }
}