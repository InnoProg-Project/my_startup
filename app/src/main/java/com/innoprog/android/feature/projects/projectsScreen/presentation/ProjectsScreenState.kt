package com.innoprog.android.feature.projects.projectsScreen.presentation

import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.util.ErrorScreenState

sealed interface ProjectsScreenState {
    data object Empty : ProjectsScreenState
    data object Loading : ProjectsScreenState
    data class Error(val errorType: ErrorScreenState) : ProjectsScreenState
    data class Content(val projects: List<Project>) : ProjectsScreenState
}
