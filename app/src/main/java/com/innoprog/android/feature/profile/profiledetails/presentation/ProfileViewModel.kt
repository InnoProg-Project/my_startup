package com.innoprog.android.feature.profile.profiledetails.presentation

import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: ProfileInfoRepo) :
    BaseViewModel() {

    private val _profileStateFlow = MutableStateFlow<Profile?>(null)
    val profileStateFlow: StateFlow<Profile?> = _profileStateFlow

    fun loadProfile() {
        viewModelScope.safeLaunch {
            _profileStateFlow.value = repository.getAndSaveProfile()
        }
    }
}