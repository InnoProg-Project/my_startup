package com.innoprog.android.util

import com.innoprog.android.R

enum class ErrorScreenState(val messageResource: Int, val imageResource: Int) {
    NO_INTERNET(
        R.string.error_no_internet_message,
        com.innoprog.android.uikit.R.drawable.ic_no_internet_error_125
    ),
    NOT_FOUND(
        R.string.error_not_found_message,
        com.innoprog.android.uikit.R.drawable.ic_page_not_found_125
    ),
    SERVER_ERROR(
        R.string.error_server_problems_message,
        com.innoprog.android.uikit.R.drawable.ic_server_error_125
    ),
}