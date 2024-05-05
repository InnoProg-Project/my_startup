package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.network.data.Response

data class ProfileCompanyResponse(
    val id: String,
    val userId: String,
    val name: String,
    val url: String,
    val role: String
) : Response()
