package com.innoprog.android.feature.profile.profiledetails.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ProfileCompany(
    val id: String,
    val userId: String,
    val name: String,
    val url: String,
    val role: String
):Parcelable
