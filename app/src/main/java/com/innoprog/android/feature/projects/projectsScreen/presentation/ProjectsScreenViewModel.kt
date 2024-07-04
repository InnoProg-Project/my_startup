package com.innoprog.android.feature.projects.projectsScreen.presentation

import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.feature.projects.projectsScreen.domain.api.GetProjectListUseCase
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectsScreenViewModel @Inject constructor(
    private val getProjectListUseCase: GetProjectListUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<ProjectsScreenState>(ProjectsScreenState.Loading)
    val state = _state.asStateFlow()

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
                _state.emit(ProjectsScreenState.Error(ErrorScreenState.SERVER_ERROR))
            }
        }
    }

    private suspend fun handleResult(result: Resource<List<Project>>) {
        when (result) {
            is Resource.Success -> handleSuccess(result)
            is Resource.Error -> handleError(result.errorType)
        }
    }

    private suspend fun handleSuccess(result: Resource.Success<List<Project>>) {
        _state.emit(
            if (result.data.isEmpty()) {
                ProjectsScreenState.Empty
            } else {
                ProjectsScreenState.Content(result.data)
            }
        )
    }

    private suspend fun handleError(errorType: ErrorType) {
        _state.emit(renderError(errorType))
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
}
