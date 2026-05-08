package com.example.healthmonitoringapp.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {
    private const val ISO_PATTERN = "yyyy-MM-dd"

    private val formatter: SimpleDateFormat
        get() = SimpleDateFormat(ISO_PATTERN, Locale.US).apply {
            isLenient = false
        }

    fun today(): String = formatter.format(Calendar.getInstance().time)

    fun daysAgo(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        return formatter.format(calendar.time)
    }

    fun isValidIsoDate(value: String): Boolean {
        return try {
            formatter.parse(value)
            value.length == ISO_PATTERN.length
        } catch (_: ParseException) {
            false
        }
    }

    fun displayDate(value: String): String {
        if (!isValidIsoDate(value)) return value
        val parsed = formatter.parse(value) ?: return value
        return SimpleDateFormat("MMM d", Locale.US).format(parsed)
    }

    fun stableIdForDate(date: String): Long {
        return date.filter(Char::isDigit).toLongOrNull() ?: System.currentTimeMillis()
    }

    fun pastWeekDates(): Set<String> = (0..6).map { daysAgo(it) }.toSet()
}
