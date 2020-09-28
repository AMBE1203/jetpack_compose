package com.example.jetcalendar.ui.calendar

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.accessibilityLabel
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.jetcalendar.data.year2020
import com.example.jetcalendar.model.CalendarDay
import com.example.jetcalendar.model.CalendarMonth
import com.example.jetcalendar.model.DayOfWeek
import com.example.jetcalendar.model.DaySelectedStatus
import com.example.jetcalendar.ui.theme.Circle
import com.example.jetcalendar.ui.theme.JetCalendarTheme
import com.example.jetcalendar.ui.theme.SemiRect
import java.time.Month
import java.time.Year

private val CELL_SIZE = 48.dp

typealias CalendarWeek = List<CalendarDay>

val DayStatusKey = SemanticsPropertyKey<DaySelectedStatus>("DayStatusKey")

var SemanticsPropertyReceiver.dayStatusProperty by DayStatusKey

private fun DaySelectedStatus.isMarked(): Boolean {
    return when (this) {
        DaySelectedStatus.Selected -> true
        DaySelectedStatus.FirstDay -> true
        DaySelectedStatus.LastDay -> true
        DaySelectedStatus.FirstLastDay -> true
        else -> false
    }
}

private fun DaySelectedStatus.color(theme: Colors) = when (this) {
    DaySelectedStatus.Selected -> theme.secondary
    else -> Color.Transparent
}

@Composable
private fun getLeftRightWeekColors(week: CalendarWeek, month: CalendarMonth): Pair<Color, Color> {
    val materialColor = MaterialTheme.colors

    val firstDayOfTheWeek = week[0].value
    val leftFillColor = if (firstDayOfTheWeek.isNotEmpty()) {
        val lastDayPreviousWeek = month.getPreviousDay(firstDayOfTheWeek.toInt())
        if (lastDayPreviousWeek?.status?.isMarked() == true && week[0].status.isMarked()) {
            materialColor.secondary
        } else {
            Color.Transparent
        }
    } else {
        Color.Transparent
    }

    val lastDayOfTheWeek = week[6].value
    val rightFillColor = if (lastDayOfTheWeek.isNotEmpty()) {
        val firstDayNextWeek = month.getNextDay(lastDayOfTheWeek.toInt())
        if (firstDayNextWeek?.status?.isMarked() == true && week[6].status.isMarked()) {
            materialColor.secondary
        } else {
            Color.Transparent
        }
    } else {
        Color.Transparent
    }

    return leftFillColor to rightFillColor
}

@Composable
private fun DayContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    children: @Composable () -> Unit
) {
    // What if this doesn't fit the screen? - LayoutFlexible(1f) + LayoutAspectRatio(1f)
    Surface(
        modifier = modifier.preferredSize(width = CELL_SIZE, height = CELL_SIZE),
        color = backgroundColor
    ) {
        children()
    }
}


@Composable
private fun Day(name: String) {
    DayContainer {
        Text(
            text = name,
            modifier = Modifier.wrapContentSize(Alignment.Center),
            style = MaterialTheme.typography.caption.copy(
                Color.White.copy(alpha = 0.6f)
            )
        )
    }
}

@Composable
private fun DayStatusContainer(
    status: DaySelectedStatus,
    children: @Composable() () -> Unit
) {
    if (status.isMarked()) {
        Stack {
            val color = MaterialTheme.colors.secondary
            Circle(color = color)
            if (status == DaySelectedStatus.FirstDay) {
                SemiRect(color = color, lookingLeft = false)
            } else if (status == DaySelectedStatus.LastDay) {
                SemiRect(color = color, lookingLeft = true)
            }
            children()
        }
    } else {
        children()
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    onDayClicked: (CalendarDay) -> Unit,
    modifier: Modifier = Modifier
) {
    val enabled = day.status != DaySelectedStatus.NonClickable
    DayContainer(modifier = modifier.clickable(enabled) {
        if (day.status != DaySelectedStatus.NonClickable) {
            onDayClicked(day)
        }

    }, backgroundColor = day.status.color(MaterialTheme.colors)) {
        DayStatusContainer(status = day.status) {
            Text(
                text = day.value,
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                style = MaterialTheme.typography.body1.copy(
                    Color.White
                )
            )
        }
    }
}

@Composable
private fun DayOfWeek(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        for (day in DayOfWeek.values()) {
            Day(name = day.name.take(1))
        }
    }
}

@Composable
private fun Week(
    modifier: Modifier = Modifier,
    month: CalendarMonth,
    week: CalendarWeek,
    onDayClicked: (CalendarDay) -> Unit
) {

    val (leftFillColor, rightFillColor) = getLeftRightWeekColors(week = week, month = month)
    Row(modifier = modifier) {
        val spaceModifiers = Modifier.weight(1f).preferredHeightIn(maxHeight = CELL_SIZE)
        Surface(modifier = spaceModifiers, color = leftFillColor) {
            Spacer(modifier = Modifier.fillMaxHeight())
        }
        for (day in week) {
            Day(
                day = day,
                onDayClicked = onDayClicked,
                modifier = Modifier.semantics {
                    accessibilityLabel = "${month.name} ${day.value}"
                    dayStatusProperty = day.status
                })
        }
        Surface(modifier = spaceModifiers, color = rightFillColor) {
            Spacer(modifier = Modifier.fillMaxHeight())

        }
    }

}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    month: String,
    year: String
) {
    Row(modifier = modifier) {
        Text(text = month, modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
        Text(
            text = year,
            modifier = Modifier.gravity(Alignment.CenterVertically),
            style = MaterialTheme.typography.caption
        )
    }
}


@Composable
private fun Month(
    modifier: Modifier = Modifier,
    month: CalendarMonth,
    onDayClicked: (CalendarDay, CalendarMonth) -> Unit
) {
    Column(modifier = modifier) {
        MonthHeader(
            month = month.name,
            year = month.year,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        val contentModifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
        DayOfWeek(modifier = contentModifier)
        for (week in month.weeks.value) {
            Week(
                month = month,
                week = week,
                onDayClicked = { day -> onDayClicked(day, month) },
                modifier = contentModifier
            )

            Spacer(Modifier.preferredHeight(8.dp))

        }
    }
}

@Composable
fun Calendar(
    onDayClicked: (CalendarDay, CalendarMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableColumn(modifier = modifier) {
        Spacer(modifier = Modifier.preferredHeight(32.dp))
        for (month in year2020) {
            Month(month = month, onDayClicked = onDayClicked)
            Spacer(modifier = Modifier.preferredHeight(32.dp))

        }
    }
}

@Preview
@Composable
fun previewDay() {
    JetCalendarTheme {
        Calendar(onDayClicked = { day, month -> })
    }
}