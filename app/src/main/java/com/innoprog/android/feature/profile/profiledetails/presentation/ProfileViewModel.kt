package com.innoprog.android.feature.profile.profiledetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsInteractor
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileCompanyUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import com.innoprog.android.util.Resource
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

    fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase.getProfile().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _uiState.postValue(ProfileScreenState.Content(response.data))
                    }

                    is Resource.Error -> {
                        _uiState.postValue(ProfileScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun loadProfileCompany() {
        viewModelScope.launch {
            getProfileCompanyUseCase.getProfileCompany().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _uiStateCompany.postValue(ProfileCompanyScreenState.Content(response.data))
                    }

                    is Resource.Error -> {
                        _uiStateCompany.postValue(ProfileCompanyScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun loadChipAll(authorId: String) {
        viewModelScope.launch {
            chipsInteractor.getAll(authorId).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _chipsUiState.postValue(ChipsScreenState.All(response.data))
                    }

                    is Resource.Error -> {
                        _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun loadChipProjects(authorId: String) {
        viewModelScope.launch {
            chipsInteractor.getProjects(authorId).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _chipsUiState.postValue(ChipsScreenState.Projects(response.data))
                    }

                    is Resource.Error -> {
                        _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun loadChipIdeas(type: String, authorId: String) {
        viewModelScope.launch {
            chipsInteractor.getIdeas(type, authorId).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _chipsUiState.postValue(ChipsScreenState.Ideas(response.data))
                    }

                    is Resource.Error -> {
                        _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun loadChipLiked(pageSize: Int) {
        viewModelScope.launch {
            chipsInteractor.getLikes(pageSize).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _chipsUiState.postValue(ChipsScreenState.Liked(response.data))
                    }

                    is Resource.Error -> {
                        _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun loadChipFavorites(pageSize: Int) {
        viewModelScope.launch {
            chipsInteractor.getFavorites(pageSize).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _chipsUiState.postValue(ChipsScreenState.Favorites(response.data))
                    }

                    is Resource.Error -> {
                        _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                    }
                }
            }
        }
    }
}
