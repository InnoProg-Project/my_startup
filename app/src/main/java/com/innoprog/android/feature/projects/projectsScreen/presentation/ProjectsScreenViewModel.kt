package com.innoprog.android.feature.projects.projectsScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innoprog.android.base.BaseViewModel
import javax.inject.Inject

class ProjectsScreenViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableLiveData<ProjectsScreenState>()
    val state: LiveData<ProjectsScreenState> get() = _state
}
