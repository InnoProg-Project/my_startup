package com.innoprog.android.feature.feed.userprojectscreen.presentation

import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.util.ErrorScreenState

sealed class UserProjectDetailsState {
    data object Empty : UserProjectDetailsState()
    data object Loading : UserProjectDetailsState()
    data class Error(val errorType: ErrorScreenState) : UserProjectDetailsState()
    data class Content(val project: AnyProjectDetailsModel) : UserProjectDetailsState()
}