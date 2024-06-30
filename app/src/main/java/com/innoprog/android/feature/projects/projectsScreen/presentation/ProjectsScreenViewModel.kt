package com.innoprog.android.feature.projects.projectsScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.base.SingleLiveEvent
import com.innoprog.android.feature.projects.projectsScreen.domain.useCase.GetProjectsUseCase
import com.innoprog.android.util.debounceFun
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectsScreenViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
) : BaseViewModel() {

    private val onProjectClickDebounce =
        debounceFun<String>(CLICK_DELAY, viewModelScope, false) { projectId ->
            showProject.postValue(projectId)
        }

    private val showProject = SingleLiveEvent<String>()

    fun getShowProjectTrigger(): SingleLiveEvent<String> = showProject

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

    fun openProject(projectId: String) {
        onProjectClickDebounce(projectId)
    }

    companion object {
        private const val CLICK_DELAY = 300L
    }
}
