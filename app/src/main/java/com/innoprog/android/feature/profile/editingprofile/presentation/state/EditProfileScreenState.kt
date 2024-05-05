package com.innoprog.android.feature.profile.editingprofile.presentation.state

import com.innoprog.android.feature.profile.editingprofile.domain.model.Body
import com.innoprog.android.util.ErrorType

sealed interface EditProfileScreenState {
    data class Content(val bodyInfo: Body) : EditProfileScreenState

    data class Error(val type: ErrorType) : EditProfileScreenState
}
