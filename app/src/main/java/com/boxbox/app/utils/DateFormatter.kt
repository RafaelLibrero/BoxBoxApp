package com.boxbox.app.utils

import android.content.Context
import com.boxbox.app.R
import com.boxbox.app.domain.model.Race
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFormatter {

    fun formatToDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
        val year = calendar.get(Calendar.YEAR)

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        return if (year == currentYear) {
            "$day $month"
        } else {
            "$day $month, $year"
        }
    }

    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        return sdf.format(date)
    }

    fun getLastAccessText(context: Context, date: Date): String {
        val lastAccessLocalDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        val now = LocalDateTime.now()
        val duration = Duration.between(lastAccessLocalDateTime, now)

        val minutes = duration.toMinutes()
        val hours = duration.toHours()
        val days = duration.toDays()
        val months = days / 30
        val years = days / 365

        val timeText = when {
            years > 0 -> context.resources.getQuantityString(R.plurals.years_ago, years.toInt(), years.toInt())
            months > 0 -> context.resources.getQuantityString(R.plurals.months_ago, months.toInt(), months.toInt())
            days > 0 -> context.resources.getQuantityString(R.plurals.days_ago, days.toInt(), days.toInt())
            hours > 0 -> context.resources.getQuantityString(R.plurals.hours_ago, hours.toInt(), hours.toInt())
            minutes > 0 -> context.resources.getQuantityString(R.plurals.minutes_ago, minutes.toInt(), minutes.toInt())
            else -> context.resources.getString(R.string.just_now)
        }

        return timeText
    }

    fun getCreatedAtText(date: Date): String {
        val createdAtLocalDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        val now = LocalDateTime.now()
        val duration = Duration.between(createdAtLocalDateTime, now)

        val minutes = duration.toMinutes()
        val hours = duration.toHours()
        val days = duration.toDays()

        val timeText = when {
            days > 0 -> formatToDate(date)
            hours > 0 -> "${hours}h"
            minutes > 0 -> "${minutes}min"
            else -> {
                "Ahora"
            }
        }

        return timeText
    }

    fun raceFormat(race: Race): String {
        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

        val dayStart = dayFormat.format(race.startDate)
        val dayEnd = dayFormat.format(race.endDate)
        val month = monthFormat.format(race.endDate)

        val displayText = "$dayStart - $dayEnd $month"

        return displayText
    }
}