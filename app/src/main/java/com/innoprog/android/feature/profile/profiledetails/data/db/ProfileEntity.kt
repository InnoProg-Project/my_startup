package com.innoprog.android.feature.profile.profiledetails.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val about: String = "",
)
