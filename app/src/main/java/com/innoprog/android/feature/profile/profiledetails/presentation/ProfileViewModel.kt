package com.innoprog.android.feature.profile.profiledetails.presentation

import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.network.domain.ApiInteractor
import javax.inject.Inject

class ProfileViewModel @Inject constructor(val apiInteractor: ApiInteractor) : BaseViewModel()
