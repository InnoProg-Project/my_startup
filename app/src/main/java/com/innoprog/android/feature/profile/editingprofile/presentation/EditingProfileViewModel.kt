package com.innoprog.android.feature.profile.editingprofile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.editingprofile.domain.SaveProfileCompanyUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.SaveProfileUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.models.Profile
import com.innoprog.android.feature.profile.editingprofile.domain.models.ProfileCompany
import com.innoprog.android.feature.profile.editingprofile.presentation.state.ProfileCompanyScreenState
import com.innoprog.android.feature.profile.editingprofile.presentation.state.ProfileScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

class EditingProfileViewModel @Inject constructor(
    private val saveProfileUseCase: SaveProfileUseCase,
    private val saveProfileCompanyUseCase: SaveProfileCompanyUseCase
) : BaseViewModel() {

    private val _uiState = MutableLiveData<ProfileScreenState>()
    val uiState: LiveData<ProfileScreenState> = _uiState

    private val _uiStateCompany = MutableLiveData<ProfileCompanyScreenState>()
    val uiStateCompany: LiveData<ProfileCompanyScreenState> = _uiStateCompany

    private var profile: Profile? = null
    private var profileCompany: ProfileCompany? = null

    fun saveProfile() {
        if (profile != null) {
            _uiState.postValue(ProfileScreenState.Content(profile as Profile))
        } else {
            _uiState.value = ProfileScreenState.Error(
                ErrorType.UNEXPECTED
            )

            viewModelScope.launch(Dispatchers.IO) {
                saveProfileUseCase.saveProfile().collect { response ->
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

    fun saveProfileCompany() {
        if (profileCompany != null) {
            _uiStateCompany.postValue(ProfileCompanyScreenState.Content(profileCompany as ProfileCompany))
        } else {
            _uiStateCompany.value = ProfileCompanyScreenState.Error(
                ErrorType.UNEXPECTED
            )

            viewModelScope.launch(Dispatchers.IO) {
                saveProfileCompanyUseCase.saveProfileCompany().collect { response ->
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
}
