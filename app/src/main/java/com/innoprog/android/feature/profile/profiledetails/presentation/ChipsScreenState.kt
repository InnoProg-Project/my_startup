package com.innoprog.android.feature.profile.profiledetails.presentation

import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.util.ErrorType

sealed interface ChipsScreenState {
    class All(val content: List<FeedWrapper>) : ChipsScreenState

    class Projects(val projects: List<FeedWrapper.News>) : ChipsScreenState

    class Ideas(val ideas: List<FeedWrapper.Idea>) : ChipsScreenState

    class Liked(val liked: List<FeedWrapper>) : ChipsScreenState

    class Favorites(val favorites: List<FeedWrapper>) : ChipsScreenState

    class Error(val type: ErrorType) : ChipsScreenState
}
