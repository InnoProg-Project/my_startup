package com.innoprog.android.feature.profile.editingprofile.presentation.state

import com.innoprog.android.feature.profile.editingprofile.domain.models.ProfileCompany
import com.innoprog.android.util.ErrorType

sealed interface ProfileCompanyScreenState{
    data class Content(val profileCompany: ProfileCompany) : ProfileCompanyScreenState

    data class Error(val type: ErrorType) : ProfileCompanyScreenState
}
