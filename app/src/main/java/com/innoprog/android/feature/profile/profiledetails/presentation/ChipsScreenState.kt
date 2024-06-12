package com.innoprog.android.feature.profile.profiledetails.presentation

import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.profile.profiledetails.domain.models.Project
import com.innoprog.android.util.ErrorType

sealed interface ChipsScreenState {
    data class All(val content: List<News>) : ChipsScreenState

    data class Projects(val projects: List<Project>) : ChipsScreenState

    data class Ideas(val ideas: List<News>) : ChipsScreenState

    data class Liked(val liked: List<News>) : ChipsScreenState

    data class Favorites(val favorites: List<News>) : ChipsScreenState

    data class Error(val type: ErrorType) : ChipsScreenState

}
