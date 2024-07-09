package com.innoprog.android.feature.profile.profiledetails.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.BuildConfig
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsInteractor
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileCompanyUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfileCompanyUseCase: GetProfileCompanyUseCase,
    private val chipsInteractor: ChipsInteractor
) : BaseViewModel() {

    private val _uiState = MutableLiveData<ProfileScreenState>()
    val uiState: LiveData<ProfileScreenState> = _uiState

    private val _uiStateCompany = MutableLiveData<ProfileCompanyScreenState>()
    val uiStateCompany: LiveData<ProfileCompanyScreenState> = _uiStateCompany

    private val _chipsUiState = MutableLiveData<ChipsScreenState>()
    val chipsUiState: LiveData<ChipsScreenState> = _chipsUiState

    fun loadProfile() {
        runSafelyUseCase<Profile> (
            getUseCaseFlow = { getProfileUseCase.getProfile() }
        ) { _uiState.postValue(ProfileScreenState.Content(it)) }
    }

    fun loadProfileCompany() {
        runSafelyUseCase<ProfileCompany> (
            getUseCaseFlow = { getProfileCompanyUseCase.getProfileCompany() }
        ) { _uiStateCompany.postValue(ProfileCompanyScreenState.Content(it)) }
    }

    fun loadChipAll(authorId: String) {
        runSafelyUseCase<List<FeedWrapper>>(
            getUseCaseFlow = { chipsInteractor.getAll(authorId) }
        ) { _chipsUiState.postValue(ChipsScreenState.All(it)) }
    }

    fun loadChipProjects(type: String, authorId: String) {
        runSafelyUseCase<List<FeedWrapper.News>>(
            getUseCaseFlow = { chipsInteractor.getProjects(type, authorId) }
        ) { _chipsUiState.postValue(ChipsScreenState.Projects(it)) }
    }

    fun loadChipIdeas(type: String, authorId: String) {
        runSafelyUseCase<List<FeedWrapper.Idea>>(
            getUseCaseFlow = { chipsInteractor.getIdeas(type, authorId) }
        ) { _chipsUiState.postValue(ChipsScreenState.Ideas(it)) }
    }

    fun loadChipLiked(pageSize: Int) {
        runSafelyUseCase<List<FeedWrapper>>(
            getUseCaseFlow = { chipsInteractor.getLikes(pageSize) }
        ) { _chipsUiState.postValue(ChipsScreenState.Liked(it)) }
    }

    fun loadChipFavorites(pageSize: Int) {
        runSafelyUseCase<List<FeedWrapper>>(
            getUseCaseFlow = { chipsInteractor.getFavorites(pageSize) }
        ) { _chipsUiState.postValue(ChipsScreenState.Favorites(it)) }
    }

    private inline fun <reified D> runSafelyUseCase(
        crossinline getUseCaseFlow: suspend () -> Flow<Resource<D>>,
        crossinline onSuccess: (D) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getUseCaseFlow().collect { result ->
                    when (result) {
                        is Resource.Success -> onSuccess(result.data)
                        is Resource.Error -> {
                            _chipsUiState.postValue(ChipsScreenState.Error(result.errorType))
                        }
                    }
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "error -> ${error.localizedMessage}")
                    error.printStackTrace()
                }
                _chipsUiState.postValue(ChipsScreenState.Error(type = ErrorType.NO_CONNECTION))
            }
        }
    }

    companion object {
        private val TAG = ProfileViewModel::class.simpleName
    }
}
