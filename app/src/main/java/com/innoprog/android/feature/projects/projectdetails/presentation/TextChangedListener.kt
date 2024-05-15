package com.innoprog.android.feature.projects.projectdetails.presentation

import android.text.Editable
import android.text.TextWatcher

interface TextChangedListener : TextWatcher {
    override fun afterTextChanged(p0: Editable?) = Unit
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
}
