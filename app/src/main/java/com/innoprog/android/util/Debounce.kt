package com.innoprog.android.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounceFun(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                if (useLastParam) {
                    delay(delayMillis)
                }
                action(param)
                if (!useLastParam) {
                    delay(delayMillis)
                }
            }
        }
    }
}

fun <T> debounceUnitFun(
    coroutineScope: CoroutineScope,
    delayMillis: Long = DEFAULT_DELAY,
    useLastParam: Boolean = false
): (T, (T) -> Unit) -> Unit {
    var debounceJob: Job? = null
    return { param: T, finalAction: (T) -> Unit ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                if (useLastParam) {
                    delay(delayMillis)
                }
                finalAction(param)
                if (!useLastParam) {
                    delay(delayMillis)
                }
            }
        }
    }
}

fun View.setOnDebouncedClickListener(
    coroutineScope: CoroutineScope,
    delayMillis: Long = DEFAULT_DELAY,
    useLastParam: Boolean = false,
    onClick: (View) -> Unit,
) {
    var debounceJob: Job? = null

    setOnClickListener { view ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                if (useLastParam) {
                    delay(delayMillis)
                }
                onClick(view)
                if (!useLastParam) {
                    delay(delayMillis)
                }
            }
        }
    }
}

const val DEFAULT_DELAY = 300L
