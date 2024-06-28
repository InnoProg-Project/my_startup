package com.innoprog.android.feature.profile.profiledetails.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Profile(
    val userId: String,
    val name: String,
    val about: String,
    val communicationChannels: List<CommunicationChannel>,
    val authorities: List<String>
): Parcelable
