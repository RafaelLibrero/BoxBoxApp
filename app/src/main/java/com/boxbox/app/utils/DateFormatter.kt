package com.boxbox.app.utils

import android.content.Context
import com.boxbox.app.R
import com.boxbox.app.domain.model.Race
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateFormatter {

    fun formatToMinutes(date: Date?): String {
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return outputFormat.format(date)
    }

    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        return sdf.format(date)
    }
    fun getRegisteredText(context: Context, date: Date): String {
        val formattedDate = formatDate(date)
        return context.getString(R.string.registered_on, formattedDate)
    }

    fun getLastAccessText(context: Context, date: Date): String {
        val now = Date()
        val diffInMillis = now.time - date.time

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
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

        return context.getString(R.string.last_access, timeText)
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