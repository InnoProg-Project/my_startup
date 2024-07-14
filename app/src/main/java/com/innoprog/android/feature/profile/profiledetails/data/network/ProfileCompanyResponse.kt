package com.innoprog.android.feature.profile.profiledetails.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.network.data.Response

data class ProfileCompanyResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("role")
    val role: String
) : Response()

fun ProfileCompanyResponse.mapToDomainCompany(): ProfileCompany {
    return ProfileCompany(
        id = id,
        userId = userId,
        name = name,
        url = url,
        role = role
    )
}


