package com.innoprog.android.feature.profile.profiledetails.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommunicationChannel(
    val id: String,
    val type: String,
    val value: String,
    val verified: Boolean,
    val main: Boolean
): Parcelable
