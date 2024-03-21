package com.innoprog.android.feature.profile.presentation

import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.data.network.ApiService
import com.innoprog.android.domain.ApiInteractor
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val apiInteractor: ApiInteractor) : BaseViewModel()