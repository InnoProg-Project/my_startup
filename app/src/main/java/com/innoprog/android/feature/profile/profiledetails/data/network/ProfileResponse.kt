package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.feature.profile.profiledetails.domain.models.CommunicationChannel
import com.innoprog.android.network.data.Response

data class ProfileResponse(
    val userId: String,
    val name: String,
    val about: String,
    val communicationChannels: List<CommunicationChannel>,
    val authorities: List<String>
) : Response()
