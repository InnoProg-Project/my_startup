package com.innoprog.android.feature.training.course_information.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.training.course_information.domain.useCase.GetCourseInformationUseCase
import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationState
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseInformationViewModel @Inject constructor(
    private val getCourseInformationUseCase: GetCourseInformationUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<CourseInformationState>()
    val state: LiveData<CourseInformationState> = _state

    init {
        setState(CourseInformationState.Load)
    }

    fun getCourseInformation(courseId: Int) {
        viewModelScope.launch {
            getCourseInformationUseCase.execute(courseId).collect {
                if (it.first != null) {
                    setState(CourseInformationState.Content(it.first!!))
                } else {
                    setState(CourseInformationState.Error)
                }
            }
        }
    }

    private fun setState(state: CourseInformationState) {
        _state.value = state
    }
}
