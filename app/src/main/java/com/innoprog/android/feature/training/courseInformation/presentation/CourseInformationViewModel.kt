package com.innoprog.android.feature.training.courseInformation.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.training.courseInformation.domain.model.Attachments
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationDocumentModel
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationImageModel
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationVideoModel
import com.innoprog.android.feature.training.courseInformation.domain.useCase.GetCourseInformationUseCase
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
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

    fun getCourseInformation(courseId: String) {
        viewModelScope.launch {
            runCatching {
                getCourseInformationUseCase.execute(courseId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            setState(CourseInformationState.Content(response.data!!))
                        }
                        is Resource.Error -> {
                            setState(CourseInformationState.Error(response.errorType))
                        }
                    }
                }
            }.onFailure { exception ->
                setState(CourseInformationState.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    fun getVideo(listAttachments: List<Attachments>): List<CourseInformationVideoModel> {
        return getCourseInformationUseCase.getVideo(listAttachments)
    }

    fun getImage(listAttachments: List<Attachments>?): List<CourseInformationImageModel> {
        return getCourseInformationUseCase.getImage(listAttachments)
    }

    fun getDocument(listAttachments: List<Attachments>): List<CourseInformationDocumentModel> {
        return getCourseInformationUseCase.getDocument(listAttachments)
    }

    private fun setState(state: CourseInformationState) {
        _state.value = state
    }
}
