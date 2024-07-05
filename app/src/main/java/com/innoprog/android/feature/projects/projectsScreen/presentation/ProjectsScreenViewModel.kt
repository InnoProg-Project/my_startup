package com.innoprog.android.feature.projects.projectsScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.feature.projects.projectsScreen.domain.api.GetProjectListUseCase
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectsScreenViewModel @Inject constructor(
    private val getProjectListUseCase: GetProjectListUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<ProjectsScreenState>(ProjectsScreenState.Loading)
    val state: LiveData<ProjectsScreenState>
        get() = _state

    init {
        getProjectList()
    }

    fun getProjectList() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getProjectListUseCase.execute()
            }.onSuccess { result ->
                handleResult(result)
            }.onFailure {
                updateState(ProjectsScreenState.Error(ErrorScreenState.SERVER_ERROR))
            }
        }
    }

    private fun handleResult(result: Resource<List<Project>>) {
        when (result) {
            is Resource.Success -> handleSuccess(result)
            is Resource.Error -> handleError(result.errorType)
        }
    }

    private fun handleSuccess(result: Resource.Success<List<Project>>) {
        if (result.data.isEmpty()) {
            updateState(ProjectsScreenState.Empty)
        } else {
            updateState(ProjectsScreenState.Content(result.data))
        }
    }

    private fun handleError(errorType: ErrorType) {
        updateState(renderError(errorType))
    }


    private fun renderError(errorType: ErrorType): ProjectsScreenState.Error {
        return when (errorType) {
            ErrorType.NO_CONNECTION -> ProjectsScreenState.Error(ErrorScreenState.NO_INTERNET)
            ErrorType.NOT_FOUND -> ProjectsScreenState.Error(ErrorScreenState.NOT_FOUND)
            ErrorType.INTERNAL_SERVER_ERROR -> ProjectsScreenState.Error(ErrorScreenState.SERVER_ERROR)
            ErrorType.UNAUTHORIZED -> ProjectsScreenState.Error(ErrorScreenState.UNAUTHORIZED)
            else -> ProjectsScreenState.Error(ErrorScreenState.SERVER_ERROR)
        }
    }

    private fun updateState(state: ProjectsScreenState) {
        _state.postValue(state)
    }
}
