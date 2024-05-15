package com.innoprog.android.feature.profile.profiledetails.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ProfileCompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfileCompany(profileCompany: ProfileCompanyEntity)
}
