package com.innoprog.android.feature.profile.profiledetails.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_company_table")
data class ProfileCompanyEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String,
    val url: String,
    val role: String
)
