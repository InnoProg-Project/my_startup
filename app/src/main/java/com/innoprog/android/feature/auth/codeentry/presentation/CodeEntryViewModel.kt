package com.innoprog.android.feature.auth.codeentry.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innoprog.android.base.BaseViewModel
import okhttp3.internal.format
import javax.inject.Inject

class CodeEntryViewModel @Inject constructor() : BaseViewModel() {
    private val stateButtonLiveData = MutableLiveData<Pair<Boolean, String>>()
    fun observeState(): LiveData<Pair<Boolean, String>> = stateButtonLiveData
    fun startTimer() {
        val timer = object : CountDownTimer(DURATION, DELAY) {
            override fun onTick(millis: Long) {
                stateButtonLiveData.postValue(
                    Pair(
                        false,
                        format("Новый код через %d сек.", millis / 1000)
                    )
                )
            }

            override fun onFinish() {
                stateButtonLiveData.postValue(Pair(true, "Получить новый код"))
            }

        }
        timer.start()
    }

    fun repeatRequest() {
        startTimer()
    }

    companion object {
        const val DELAY = 1000L
        const val DURATION = 15000L
    }
}
