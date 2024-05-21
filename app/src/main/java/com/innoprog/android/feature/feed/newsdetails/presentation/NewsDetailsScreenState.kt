package com.innoprog.android.feature.feed.newsdetails.presentation

import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel

sealed interface NewsDetailsScreenState {
    data object Loading : NewsDetailsScreenState

    data object Error : NewsDetailsScreenState

    data class Content(
        val newsDetails: NewsDetailsModel?,
    ) : NewsDetailsScreenState
}
