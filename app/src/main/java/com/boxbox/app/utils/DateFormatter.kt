package com.boxbox.app.utils

import android.content.Context
import com.boxbox.app.R
import com.boxbox.app.domain.model.Race
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFormatter {

    private fun formatToHourMinutes(date: Date): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

    fun formatToShortDate(date: Date): String {
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

    fun formatToLongDate(date: Date): String {
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault())
        return localDate.format(formatter)
    }

    fun getLastAccessText(context: Context, date: Date): String {
        val units = calculateTimeUnits(date)

        val timeText = when {
            units.years > 0 -> context.resources.getQuantityString(R.plurals.years_ago, units.years, units.years)
            units.months > 0 -> context.resources.getQuantityString(R.plurals.months_ago, units.months, units.months)
            units.days > 0 -> context.resources.getQuantityString(R.plurals.days_ago, units.days, units.days)
            units.hours > 0 -> context.resources.getQuantityString(R.plurals.hours_ago, units.hours, units.hours)
            units.minutes > 0 -> context.resources.getQuantityString(R.plurals.minutes_ago, units.minutes, units.minutes)
            else -> context.resources.getString(R.string.just_now)
        }

        return timeText
    }

    fun getCreatedAtText(date: Date): String {
        val units = calculateTimeUnits(date)

        val timeText = when {
            units.days > 0 -> formatToShortDate(date)
            units.hours > 0 -> "${units.hours}h"
            units.minutes > 0 -> "${units.minutes}min"
            else -> {
                "Ahora"
            }
        }

        return timeText
    }

    fun getLastMessageDate(date: Date): String {
        val units = calculateTimeUnits(date)

        val timeText = when {
            units.days > 1 -> formatToShortDate(date)
            units.days > 0 -> "Ayer"
            else -> {
                formatToHourMinutes(date)
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

    private fun calculateTimeDelta(date: Date): Duration {
        val localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        val now = LocalDateTime.now()
        return Duration.between(localDateTime, now)
    }

    private fun calculateTimeUnits(date: Date): TimeUnits {
        val delta = calculateTimeDelta(date)
        val minutes = delta.toMinutes().toInt()
        val hours = delta.toHours().toInt()
        val days = delta.toDays().toInt()
        val months = days / 30
        val years = days / 365
        return TimeUnits(minutes, hours, days, months, years)
    }

    private data class TimeUnits(
        val minutes: Int,
        val hours: Int,
        val days: Int,
        val months: Int,
        val years: Int
    )
}