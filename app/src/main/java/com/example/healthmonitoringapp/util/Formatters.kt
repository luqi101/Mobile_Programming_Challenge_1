package com.example.healthmonitoringapp.util

import java.util.Locale

fun Double.formatOneDecimal(): String = String.format(Locale.US, "%.1f", this)

fun Double.formatSignedOneDecimal(): String {
    val sign = if (this > 0.0) "+" else ""
    return "$sign${formatOneDecimal()}"
}

fun Int.formatWholeNumber(): String = String.format(Locale.US, "%,d", this)
