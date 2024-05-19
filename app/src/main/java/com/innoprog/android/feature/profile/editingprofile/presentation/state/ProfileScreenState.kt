package com.innoprog.android.feature.profile.editingprofile.presentation.state


import com.innoprog.android.feature.profile.editingprofile.domain.models.Profile
import com.innoprog.android.util.ErrorType

sealed interface ProfileScreenState{
    data class Content(val profileInfo: Profile) : ProfileScreenState

    data class Error(val type: ErrorType) : ProfileScreenState
}