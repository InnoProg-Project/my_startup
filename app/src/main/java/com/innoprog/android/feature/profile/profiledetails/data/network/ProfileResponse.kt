package com.innoprog.android.feature.profile.profiledetails.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.profile.profiledetails.domain.models.CommunicationChannel
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.network.data.Response

data class ProfileResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("about")
    val about: String,
    @SerializedName("communicationChannels")
    val communicationChannels: List<CommunicationChannel>?,
    @SerializedName("authorities")
    val authorities: List<String>?
) : Response()

fun ProfileResponse.mapToDomainUserData(): Profile {
    return Profile(
        userId = userId,
        name = name,
        about = about,
        communicationChannels = communicationChannels ?: emptyList(),
        authorities = authorities ?: emptyList()
    )
}
