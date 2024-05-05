package com.innoprog.android.feature.profile.editingprofile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.editingprofile.domain.model.Body
import com.innoprog.android.feature.profile.editingprofile.domain.EditBodyProfileUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileCompanyUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.model.CompanyBody
import com.innoprog.android.feature.profile.editingprofile.presentation.state.EditProfileCompanyScreenState
import com.innoprog.android.feature.profile.editingprofile.presentation.state.EditProfileScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditingProfileViewModel @Inject constructor(
    private val editBodyProfileUseCase: EditBodyProfileUseCase,
    private val editProfileCompanyUseCase: EditProfileCompanyUseCase
) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<EditProfileScreenState>()
    val uiState: LiveData<EditProfileScreenState> = _uiState

    private val _uiStateCompany = MutableLiveData<EditProfileCompanyScreenState>()
    val uiStateCompany: LiveData<EditProfileCompanyScreenState> = _uiStateCompany

    private var companyBody: CompanyBody? = null
    private var body: Body? = null
    fun editProfile() {
        if (body != null) {
            _uiState.postValue(EditProfileScreenState.Content(body as Body))
        } else {
            _uiState.value = EditProfileScreenState.Error(ErrorType.NOT_FOUND)
        }
        viewModelScope.launch {
            editBodyProfileUseCase.editProfile().collect() { response ->
                when (response) {
                    is Resource.Success -> {
                        _uiState.postValue(EditProfileScreenState.Content(response.data))
                        body = response.data
                    }

                    is Resource.Error -> {
                        _uiState.postValue(EditProfileScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun editProfileCompany() {
        if (companyBody != null) {
            _uiStateCompany.postValue(EditProfileCompanyScreenState.Content(companyBody as CompanyBody))
        } else {
            _uiStateCompany.value = EditProfileCompanyScreenState.Error(ErrorType.NOT_FOUND)
        }
        viewModelScope.launch {
            editProfileCompanyUseCase.editProfileCompany().collect() { response ->
                when (response) {
                    is Resource.Success -> {
                        _uiStateCompany.postValue(EditProfileCompanyScreenState.Content(response.data))
                        companyBody = response.data
                    }

                    is Resource.Error -> {
                        _uiStateCompany.postValue(EditProfileCompanyScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }
}
