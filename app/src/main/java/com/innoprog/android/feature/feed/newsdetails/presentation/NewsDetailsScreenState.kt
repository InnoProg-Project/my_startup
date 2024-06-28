package com.innoprog.android.feature.feed.newsdetails.presentation

import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.util.ErrorType

sealed interface NewsDetailsScreenState {
    data object Loading : NewsDetailsScreenState

    class Error(val type: ErrorType) : NewsDetailsScreenState

    class Content(
        val newsDetails: NewsDetailsModel,
        val comments: List<CommentModel>
    ) : NewsDetailsScreenState
}
