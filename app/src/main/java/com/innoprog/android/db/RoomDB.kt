package com.innoprog.android.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.innoprog.android.feature.feed.newsfeed.data.converters.Converters
import com.innoprog.android.feature.feed.newsfeed.data.db.NewsDao
import com.innoprog.android.feature.feed.newsfeed.data.db.NewsEntity
import com.innoprog.android.feature.profile.profiledetails.data.db.ListConverter
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileCompanyDao
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileCompanyEntity
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileDao
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileEntity

@Database(
    entities = [
        NewsEntity::class,
        ProfileEntity::class,
        ProfileCompanyEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class, ListConverter::class)
abstract class RoomDB : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun profileDao(): ProfileDao
    abstract fun profileCompanyDao(): ProfileCompanyDao
}
