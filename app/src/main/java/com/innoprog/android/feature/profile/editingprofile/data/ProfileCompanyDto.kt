package com.innoprog.android.feature.profile.editingprofile.data

import com.innoprog.android.network.data.Response

data class ProfileCompanyDto(
    val body: CompanyBodyResponse,
    val userId: String
)

data class CompanyBodyResponse(
    val name: String,
    val role: String,
    val url: String
) : Response()
