package com.innoprog.android.feature.profile.profiledetails.presentation

import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWithProject
import com.innoprog.android.util.ErrorType

sealed interface ChipsScreenState {
    class All(val content: List<FeedWithProject>) : ChipsScreenState

    class Projects(val projects: List<FeedWithProject>) : ChipsScreenState

    class Ideas(val ideas: List<FeedWithProject>) : ChipsScreenState

    class Liked(val liked: List<FeedWithProject>) : ChipsScreenState

    class Favorites(val favorites: List<FeedWithProject>) : ChipsScreenState

    class Error(val type: ErrorType) : ChipsScreenState
}
