package com.innoprog.android.feature.profile.profiledetails.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile

interface ProfileInfoRepo {

    suspend fun getAndSaveProfile(): Profile
}
