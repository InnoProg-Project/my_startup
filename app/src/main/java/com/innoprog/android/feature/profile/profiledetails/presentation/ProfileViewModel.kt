package com.innoprog.android.feature.profile.profiledetails.presentation

import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: ProfileInfoRepo) :
    BaseViewModel() {

    private val _stateFlow = MutableStateFlow<Profile?>(null)
    //val stateFlow: StateFlow<Profile?> = _stateFlow

    fun loadProfile() {
        viewModelScope.safeLaunch {
            val profile = repository.getAndSaveProfile()
            withContext(Dispatchers.Main) {
                _stateFlow.value = profile
            }
        }
    }
}
