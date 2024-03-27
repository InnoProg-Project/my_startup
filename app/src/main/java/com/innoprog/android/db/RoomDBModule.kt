package com.innoprog.android.db

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomDBModule {

    @Provides
    @Singleton
    fun provideRoomDataBaseBuilder(app: Application): RoomDB {
        return Room.databaseBuilder(
            app,
            RoomDB::class.java,
            RoomConstants.DATA_BASE_NAME
        ).allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsDao(appDatabase: RoomDB) = appDatabase.newsDao()
}
