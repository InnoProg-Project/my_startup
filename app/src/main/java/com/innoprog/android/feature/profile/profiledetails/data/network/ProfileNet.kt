package com.innoprog.android.feature.profile.profiledetails.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.profile.profiledetails.domain.models.CommunicationChannel

class ProfileNet(

    @SerializedName("userId")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("about")
    val about: String,

    @SerializedName("communicationChannel")
    val communicationChannels: List<CommunicationChannel>,

    @SerializedName("authorities")
    val authorities: List<String>
)