package com.innoprog.android.base

import androidx.fragment.app.Fragment

interface NavEvent {
    fun navigate(fragment: Fragment?)
}
