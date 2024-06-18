package com.innoprog.android.feature.projects.create.fillMainProjectInformation.presentation

import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.projects.create.fillMainProjectInformation.presentation.model.FillAboutProjectEvent
import com.innoprog.android.feature.projects.create.fillMainProjectInformation.presentation.model.FillAboutProjectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FillAboutProjectViewModel @Inject constructor() : BaseViewModel() {
    private val _state = MutableStateFlow(FillAboutProjectState())
    val state: StateFlow<FillAboutProjectState> get() = _state.asStateFlow()

    fun obtainEvent(event: FillAboutProjectEvent) {
        when (event) {
            is FillAboutProjectEvent.PickPhoto -> {
                _state.update { it.copy(pinedLogoUri = event.uri) }
            }

            is FillAboutProjectEvent.UnPinePhoto -> {
                _state.update { it.copy(pinedLogoUri = null) }
            }
        }
    }
}
