package com.innoprog.android.feature.feed.newsdetails.presentation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

private const val MIN_WIDTH = 0
private const val MAX_WIDTH = 9

fun getFormattedDate(inputDate: String): String {
    val baseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val fractionalSecondFormatter = DateTimeFormatterBuilder()
        .append(baseFormatter)
        .appendFraction(
            ChronoField.NANO_OF_SECOND,
            MIN_WIDTH,
            MAX_WIDTH,
            true
        )
        .toFormatter()

    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy в HH:mm", Locale("ru"))
    val dateTime = runCatching {
        LocalDateTime.parse(inputDate, fractionalSecondFormatter)
    }
    return dateTime.getOrNull()?.format(outputFormatter) ?: "-"
}
