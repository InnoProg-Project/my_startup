package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.db.RoomDB
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileEntity
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileApi
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import javax.inject.Inject

class ProfileInfoRepoImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val roomDB: RoomDB
) : ProfileInfoRepo {

    override suspend fun getAndSaveProfile(): Profile {
        val profileNet = profileApi.loadProfile()
        roomDB.profileDao().saveProfile(
            ProfileEntity(
                userId = profileNet.userId,
                name = profileNet.name,
                about = profileNet.about,
                communicationChannels = profileNet.communicationChannels,
                authorities = profileNet.authorities

            )
        )
        return Profile(
            userId = profileNet.userId,
            name = profileNet.name,
            about = profileNet.about,
            communicationChannels = profileNet.communicationChannels,
            authorities = profileNet.authorities
        )
    }
}
