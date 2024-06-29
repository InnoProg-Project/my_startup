package com.innoprog.android.feature.feed.userprojectscreen.presentation

import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.util.ErrorScreenState
import com.innoprog.android.util.ErrorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserProjectViewModel @Inject constructor() : BaseViewModel() {
    private val _state = MutableStateFlow<UserProjectDetailsState>(UserProjectDetailsState.Loading)
    val state = _state.asStateFlow()

    fun getProjectDetails(id: String?) {
        if (id.isNullOrEmpty()) {
            _state.value = UserProjectDetailsState.Empty
        } else {
            _state.value = UserProjectDetailsState.Content(project)
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
    shortDescription = "shortDescription",
    description = "description",
    documents = listOf(),
    financingStage = "",
    deadline = "2024-09-29",
    siteUrls = listOf()
)