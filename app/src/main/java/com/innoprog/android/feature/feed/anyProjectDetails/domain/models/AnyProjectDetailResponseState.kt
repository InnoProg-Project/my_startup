package com.innoprog.android.feature.feed.anyProjectDetails.domain.models

sealed interface AnyProjectDetailResponseState {

    data object LOADING : AnyProjectDetailResponseState

    data class Content(val anyProjectDetails: AnyProjectDetailsModel) :
        AnyProjectDetailResponseState

    data object CONNECTION_ERROR : AnyProjectDetailResponseState

}
