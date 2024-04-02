package com.innoprog.android.db

import android.app.Application
import androidx.room.Room
import com.innoprog.android.feature.feed.data.db.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomDBModule {

    @Provides
    fun provideRoomDataBaseBuilder(app: Application): RoomDB {
        return Room.databaseBuilder(
            app,
            RoomDB::class.java,
            RoomConstants.DATA_BASE_NAME
        ).allowMainThreadQueries()
            .build()
    }
}
