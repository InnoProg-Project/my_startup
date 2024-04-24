package com.innoprog.android.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.innoprog.android.feature.feed.newsfeed.data.converters.Converters
import com.innoprog.android.feature.feed.newsfeed.data.db.NewsDao
import com.innoprog.android.feature.feed.newsfeed.data.db.NewsEntity

@Database(
    entities = [
        NewsEntity::class,

    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}
