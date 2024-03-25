package com.innoprog.android.di

import com.innoprog.android.db.RoomDBModule
import com.innoprog.android.feature.feed.domain.models.FavoritesInteractor
import com.innoprog.android.feature.feed.domain.models.FavoritesRepository
import dagger.Component

@Component(
    modules = [RoomDBModule::class]
)

interface AppComponent : DIComponent {
    fun favoritesRepository(): FavoritesRepository
    fun favoritesInteractor(): FavoritesInteractor
}

interface DIComponent

// Эта сущность нужна чтобы хранить AppComponent в памяти и подсовывать в наши ScreenComponent'ы
object AppComponentHolder : ComponentHolder<AppComponent>() {
    override fun buildComponent(): AppComponent = DaggerAppComponent.builder().build()
}
