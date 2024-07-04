package com.innoprog.android.feature.feed.userprojectscreen.presentation

import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.util.ErrorScreenState

sealed class UserProjectDetailsState {
    data object Empty : UserProjectDetailsState()
    data object Loading : UserProjectDetailsState()
    class Error(val errorType: ErrorScreenState) : UserProjectDetailsState()
    class Content(val project: AnyProjectDetailsModel) : UserProjectDetailsState()
}