package com.innoprog.android.feature.profile.profiledetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsInteractor
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileCompanyUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
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

    fun loadProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
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
            }.onFailure {
                _uiState.postValue(ProfileScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    fun loadProfileCompany() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
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
            }.onFailure {
                _uiStateCompany.postValue(ProfileCompanyScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    fun loadChipAll(authorId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                chipsInteractor.getAll(authorId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _chipsUiState.postValue(ChipsScreenState.All(response.data))
                        }

                        is Resource.Error -> {
                            _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure {
                _chipsUiState.postValue(ChipsScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    fun loadChipProjects(type: String, authorId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                chipsInteractor.getProjects(type, authorId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _chipsUiState.postValue(ChipsScreenState.Projects(response.data))
                        }

                        is Resource.Error -> {
                            _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure {
                _chipsUiState.postValue((ChipsScreenState.Error(type = ErrorType.NO_CONNECTION)))
            }
        }
    }

    fun loadChipIdeas(type: String, authorId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {

                chipsInteractor.getIdeas(type, authorId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _chipsUiState.postValue(ChipsScreenState.Ideas(response.data))
                        }

                        is Resource.Error -> {
                            _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure {
                _chipsUiState.postValue(ChipsScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    fun loadChipLiked(pageSize: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                chipsInteractor.getLikes(pageSize).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _chipsUiState.postValue(ChipsScreenState.Liked(response.data))
                        }

                        is Resource.Error -> {
                            _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure {
                _chipsUiState.postValue(ChipsScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    fun loadChipFavorites(pageSize: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                chipsInteractor.getFavorites(pageSize).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _chipsUiState.postValue(ChipsScreenState.Favorites(response.data))
                        }

                        is Resource.Error -> {
                            _chipsUiState.postValue(ChipsScreenState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure {
                _chipsUiState.postValue(ChipsScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }
}
