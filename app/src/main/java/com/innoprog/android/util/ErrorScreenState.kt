package com.innoprog.android.util

enum class ErrorScreenState(val messageResource: Int, val imageResource: Int) {
    NO_INTERNET(
        com.innoprog.android.uikit.R.string.error_no_internet_message,
        com.innoprog.android.uikit.R.drawable.ic_error_no_internet_125
    ),
    NOT_FOUND(
        com.innoprog.android.uikit.R.string.error_not_found_message,
        com.innoprog.android.uikit.R.drawable.ic_error_page_not_found_125
    ),
    SERVER_ERROR(
        com.innoprog.android.uikit.R.string.error_server_problems_message,
        com.innoprog.android.uikit.R.drawable.ic_error_server_error_125
    ),
    UNAUTHORIZED(-1, -1)
}
