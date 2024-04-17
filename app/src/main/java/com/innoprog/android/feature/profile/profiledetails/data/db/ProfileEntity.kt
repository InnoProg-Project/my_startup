package com.innoprog.android.feature.profile.profiledetails.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.innoprog.android.feature.profile.profiledetails.data.db.converters.ListConverter
import com.innoprog.android.feature.profile.profiledetails.domain.models.CommunicationChannel

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val about: String,
    @TypeConverters(ListConverter::class)
    val communicationChannels: List<CommunicationChannel>,
    val authorities: List<String>
)
