package com.innoprog.android.feature.feed.newsfeed.presentation

import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.util.ErrorType

sealed interface FeedScreenState {
    data object Loading : FeedScreenState

    class Error(val type: ErrorType) : FeedScreenState

    class Content(val newsFeed: List<News>) : FeedScreenState
}
