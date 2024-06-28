package com.innoprog.android.feature.profile.editingprofile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileCompanyUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.presentation.ProfileCompanyScreenState
import com.innoprog.android.feature.profile.profiledetails.presentation.ProfileScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditingProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val editProfileCompanyUseCase: EditProfileCompanyUseCase
) : BaseViewModel() {

    private val _uiState = MutableLiveData<ProfileScreenState>()
    val uiState: LiveData<ProfileScreenState> = _uiState

    private val _uiStateCompany = MutableLiveData<ProfileCompanyScreenState>()
    val uiStateCompany: LiveData<ProfileCompanyScreenState> = _uiStateCompany

    fun editProfile(name: String, about: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                editProfileUseCase.editProfile(name, about).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _uiState.postValue(ProfileScreenState.Content(response.data))
                        }

                        is Resource.Error -> {
                            _uiState.postValue(ProfileScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure {
                _uiState.postValue(ProfileScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    fun editProfileCompany(name: String, url: String, role: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                editProfileCompanyUseCase.editProfileCompany(name, url, role).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _uiStateCompany.postValue(ProfileCompanyScreenState.Content(response.data))
                        }

                        is Resource.Error -> {
                            _uiStateCompany.postValue(ProfileCompanyScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure {
                _uiStateCompany.postValue(ProfileCompanyScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    fun fetchUserData(userId: String) {

    }
}
