package com.innoprog.android.uikit

enum class InnoProgInputViewState(val number: Int) {
    INACTIVE(CONST_INACTIVE), DISABLED(CONST_DISABLED), ERROR(CONST_ERROR), FOCUSED(CONST_FOCUSED)
}

const val CONST_INACTIVE = 0
const val CONST_DISABLED = 1
const val CONST_ERROR = 2
const val CONST_FOCUSED = 3
