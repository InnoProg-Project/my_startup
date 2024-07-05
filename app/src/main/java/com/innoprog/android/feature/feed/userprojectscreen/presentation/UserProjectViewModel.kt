package com.innoprog.android.feature.feed.userprojectscreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import javax.inject.Inject

class UserProjectViewModel @Inject constructor() : BaseViewModel() {
    private val _state = MutableLiveData<UserProjectDetailsState>(UserProjectDetailsState.Loading)
    val state: LiveData<UserProjectDetailsState>
        get() = _state

    fun getProjectDetails(id: String?) {
        if (id.isNullOrEmpty()) {
            _state.postValue(UserProjectDetailsState.Empty)
        } else {
            _state.postValue(UserProjectDetailsState.Content(project))
        }
    }

    /**
     * Подключить этот метод после подключения api
     */
    @Suppress("Detekt.UnusedPrivateMember")
    private fun renderError(errorType: ErrorType) = UserProjectDetailsState.Error(
        when (errorType) {
            ErrorType.NO_CONNECTION -> ErrorScreenState.NO_INTERNET
            ErrorType.NOT_FOUND -> ErrorScreenState.NOT_FOUND
            ErrorType.INTERNAL_SERVER_ERROR -> ErrorScreenState.SERVER_ERROR
            ErrorType.UNAUTHORIZED -> ErrorScreenState.SERVER_ERROR
            else -> ErrorScreenState.SERVER_ERROR
        }
    )
}

val project = AnyProjectDetailsModel(
    id = "123",
    images = listOf(),
    name = "Project Name",
    projectLogoFilePath = "",
    area = "Russia",
    shortDescription = "shortDescription project",
    description = "Long description of project",
    documents = listOf(),
    financingStage = "",
    deadline = "2024-09-29",
    siteUrls = listOf(
        "https://www.youtube.com/",
        "https://leetcode.com/",
        "https://www.instagram.com/"
    )
)