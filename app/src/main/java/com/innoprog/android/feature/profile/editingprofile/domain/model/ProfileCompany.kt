package com.innoprog.android.feature.profile.editingprofile.domain.model

data class ProfileCompany(
    val body: CompanyBody,
    val userId: String
)

data class CompanyBody(
    val name: String,
    val role: String,
    val url: String
)
