package com.innoprog.android.feature.projects.projectsScreen.presentation

import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.projects.projectsScreen.domain.api.GetProjectListUseCase
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
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
        viewModelScope.launch {
            renderState()
        }
    }


    private suspend fun renderState() {
        getProjectListUseCase.execute("01", null, 10).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.value = ProjectsScreenState.Content(resource.data)
                }

                is Resource.Error -> {
                    _state.value = renderError(resource as ErrorType)
                }
            }
        }
    }

    private fun renderError(errorType: ErrorType) : ProjectsScreenState.Error {
        return when(errorType) {
            ErrorType.NO_CONNECTION -> ProjectsScreenState.Error(ErrorScreenState.NO_INTERNET)
            ErrorType.NOT_FOUND -> ProjectsScreenState.Error(ErrorScreenState.NOT_FOUND)
            else -> ProjectsScreenState.Error(ErrorScreenState.SERVER_ERROR)
        }
    }
}
