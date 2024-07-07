package com.innoprog.android.feature.training.courseInformation.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.BuildConfig
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.training.courseInformation.domain.useCase.GetCourseInformationUseCase
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getCourseInformationUseCase.execute(courseId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            setState(CourseInformationState.Content(response.data!!))
                        }

                        is Resource.Error -> handleError(response.errorType)
                    }
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "error -> ${error.localizedMessage}")
                    error.printStackTrace()
                }
                setState(CourseInformationState.Error(ErrorScreenState.SERVER_ERROR))
            }
        }
    }

    private fun handleError(errorType: ErrorType) {
        setState(renderError(errorType))
    }


    private fun renderError(errorType: ErrorType): CourseInformationState.Error {
        return when (errorType) {
            ErrorType.NO_CONNECTION -> CourseInformationState.Error(ErrorScreenState.NO_INTERNET)
            ErrorType.NOT_FOUND -> CourseInformationState.Error(ErrorScreenState.NOT_FOUND)
            ErrorType.INTERNAL_SERVER_ERROR -> CourseInformationState.Error(ErrorScreenState.SERVER_ERROR)
            ErrorType.UNAUTHORIZED -> CourseInformationState.Error(ErrorScreenState.UNAUTHORIZED)
            else -> CourseInformationState.Error(ErrorScreenState.SERVER_ERROR)
        }
    }

    private fun setState(state: CourseInformationState) {
        _state.postValue(state)
    }

    fun formatAuthorName(authorName: String): String {
        val initials = authorName.split(' ').map { it.first().uppercaseChar() }
            .joinToString(separator = "", limit = 2)
        return initials.ifBlank { "?" }
    }

    companion object {
        private val TAG = CourseInformationViewModel::class.simpleName
    }
}
