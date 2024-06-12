package com.innoprog.android.feature.profile.profiledetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsInteractor
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileCompanyUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfileCompanyUseCase: GetProfileCompanyUseCase,
    private val chipsInteractor: ChipsInteractor
) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<ProfileScreenState>()
    val uiState: LiveData<ProfileScreenState> = _uiState

    private val _uiStateCompany = MutableLiveData<ProfileCompanyScreenState>()
    val uiStateCompany: LiveData<ProfileCompanyScreenState> = _uiStateCompany

    private val _chipsUiState = MutableLiveData<ChipsScreenState>()
    val chipsUiState: LiveData<ChipsScreenState> = _chipsUiState

    private var profile: Profile? = null
    private var profileCompany: ProfileCompany? = null

    fun loadProfile() {
        if (profile != null) {
            _uiState.postValue(ProfileScreenState.Content(profile as Profile))
        } else {
            _uiState.value = ProfileScreenState.Error(ErrorType.UNEXPECTED)

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

    fun loadProfileCompany() {
        if (profileCompany != null) {
            _uiStateCompany.postValue(ProfileCompanyScreenState.Content(profileCompany as ProfileCompany))
        } else {
            _uiStateCompany.value = ProfileCompanyScreenState.Error(ErrorType.UNEXPECTED)

            viewModelScope.launch(Dispatchers.IO) {
                getProfileCompanyUseCase.getProfileCompany().collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _uiStateCompany.postValue(ProfileCompanyScreenState.Content(response.data))
                            profileCompany = response.data
                        }

                        is Resource.Error -> {
                            _uiStateCompany.postValue(ProfileCompanyScreenState.Error(response.errorType))
                        }
                    }
                }
            }
        }
    }

    fun loadChips() {

    }
}
