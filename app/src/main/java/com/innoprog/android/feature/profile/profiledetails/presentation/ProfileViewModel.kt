package com.innoprog.android.feature.profile.profiledetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<ProfileScreenState>()
    val uiState: LiveData<ProfileScreenState> = _uiState
    private var profile: Profile? = null

    fun loadProfile() {
        if (profile != null) {
            _uiState.postValue(ProfileScreenState.Content(profile as Profile))
        } else {
            _uiState.value = ProfileScreenState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                getProfileUseCase.getProfile().collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _uiState.postValue(ProfileScreenState.Content(response.data))
                            profile = response.data
                        }

                        is Resource.Error -> {
                                _uiState.postValue(ProfileScreenState.Error(response.errorType))
                        }
                    }
                }
            }
        }
    }
}