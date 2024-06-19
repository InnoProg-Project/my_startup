package com.innoprog.android.feature.profile.profiledetails.presentation

import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.util.ErrorType

sealed interface ChipsScreenState {
    data class All(val content: List<FeedWrapper>) : ChipsScreenState

    data class Projects(val projects: List<FeedWrapper.News>) : ChipsScreenState

    data class Ideas(val ideas: List<FeedWrapper.Idea>) : ChipsScreenState

    data class Liked(val liked: List<FeedWrapper>) : ChipsScreenState

    data class Favorites(val favorites: List<FeedWrapper>) : ChipsScreenState

    data class Error(val type: ErrorType) : ChipsScreenState
}
