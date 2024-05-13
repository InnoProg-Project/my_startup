package com.innoprog.android.feature.profile.profiledetails.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.innoprog.android.feature.profile.profiledetails.domain.models.CommunicationChannel

class ListConverter {

    @TypeConverter
    fun fromCommunicationChannelList(communicationChannels: List<CommunicationChannel>): String {
        return Gson().toJson(communicationChannels)
    }

    @TypeConverter
    fun toCommunicationChannelList(json: String): List<CommunicationChannel> {
        return Gson().fromJson(json, Array<CommunicationChannel>::class.java).toList()
    }

    @TypeConverter
    fun fromAuthoritiesList(authorities: List<String>): String {
        return authorities.joinToString(",")
    }

    @TypeConverter
    fun toAuthoritiesList(authorities: String): List<String> {
        return authorities.split(",")
    }
}
