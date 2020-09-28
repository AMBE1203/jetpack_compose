package com.example.jetcalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.example.jetcalendar.model.CalendarDay
import com.example.jetcalendar.model.CalendarMonth
import com.example.jetcalendar.model.DaySelected
import com.example.jetcalendar.model.ServiceLocator
import com.example.jetcalendar.ui.calendar.Calendar
import com.example.jetcalendar.ui.theme.JetCalendarTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CalendarScreen()

                }
            }
        }
    }
}

private val onDayClicked: (CalendarDay, CalendarMonth) -> Unit = { day, month ->
    ServiceLocator.datesSelected.daySelected(
        DaySelected(day = day.value.toInt(), month = month)
    )
}

@Composable
fun CalendarScreen(){
    Surface (color = MaterialTheme.colors.primaryVariant){
        Calendar(onDayClicked = onDayClicked)
    }
}

