package com.innoprog.android.feature.feed.projectScreen.presentation

import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectModel
import com.innoprog.android.util.ErrorType

sealed interface AnyProjectScreenState {
    data object Loading : AnyProjectScreenState

    data class Error(val type: ErrorType) : AnyProjectScreenState

    data class Content(val anyProject: AnyProjectModel) : AnyProjectScreenState
}
