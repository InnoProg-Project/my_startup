package com.innoprog.android.feature.feed.newsdetails.presentation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

fun getFormattedDate(inputDate: String): String {
    val baseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val fractionalSecondFormatter = DateTimeFormatterBuilder()
        .append(baseFormatter)
        .appendFraction(
            ChronoField.NANO_OF_SECOND,
            NewsDetailsFragment.MIN_WIDTH,
            NewsDetailsFragment.MAX_WIDTH,
            true
        )
        .toFormatter()

    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy в HH:mm", Locale("ru"))
    val dateTime = LocalDateTime.parse(inputDate, fractionalSecondFormatter)
    return dateTime.format(outputFormatter)
}
