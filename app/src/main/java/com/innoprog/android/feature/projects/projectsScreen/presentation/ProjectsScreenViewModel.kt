package com.innoprog.android.feature.projects.projectsScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.projects.projectsScreen.domain.useCase.GetProjectsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectsScreenViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
) : BaseViewModel() {

    private val _state = MutableLiveData<ProjectsScreenState>()
    val state: LiveData<ProjectsScreenState> get() = _state

    init {
        viewModelScope.launch {
            getProjectsUseCase.execute().collect {
                if (it.first != null) {
                    _state.value = ProjectsScreenState.Content(it.first!!)
                } else {
                    _state.value = ProjectsScreenState.Empty
                }
            }
        }
    }
}
