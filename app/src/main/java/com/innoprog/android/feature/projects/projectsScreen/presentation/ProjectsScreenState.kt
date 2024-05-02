package com.innoprog.android.feature.projects.projectsScreen.presentation

import com.innoprog.android.feature.projects.projectsScreen.domain.model.ProjectScreenModel

sealed interface ProjectsScreenState {
    data object Empty : ProjectsScreenState
    data class Content(val projects: List<ProjectScreenModel>) : ProjectsScreenState
}
