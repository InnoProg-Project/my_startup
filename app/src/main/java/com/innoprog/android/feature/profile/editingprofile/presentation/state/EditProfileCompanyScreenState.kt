package com.innoprog.android.feature.profile.editingprofile.presentation.state

import com.innoprog.android.feature.profile.editingprofile.domain.model.CompanyBody
import com.innoprog.android.util.ErrorType

sealed interface EditProfileCompanyScreenState {
    data class Content(val bodyInfo: CompanyBody) : EditProfileCompanyScreenState

    data class Error(val type: ErrorType) : EditProfileCompanyScreenState
}
