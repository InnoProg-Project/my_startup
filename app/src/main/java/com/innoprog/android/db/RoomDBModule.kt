package com.innoprog.android.db

import android.app.Application
import androidx.room.Room
import com.innoprog.android.feature.feed.data.db.FavoritesRepositoryImpl
import com.innoprog.android.feature.feed.domain.models.FavoritesInteractorImpl
import com.innoprog.android.feature.feed.domain.models.FavoritesRepository
import com.innoprog.android.utils.NewsEntityMapper
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

    @Provides
    fun provideNewsConverter(): NewsEntityMapper {
        return NewsEntityMapper()
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        appDatabase: RoomDB,
        newsConverter: NewsEntityMapper
    ): FavoritesRepositoryImpl {
        return FavoritesRepositoryImpl(appDatabase, newsConverter)
    }

    @Provides
    @Singleton
    fun provideFavoritesInteractor(favoritesRepository: FavoritesRepository): FavoritesInteractorImpl {
        return FavoritesInteractorImpl(favoritesRepository)
    }
}
