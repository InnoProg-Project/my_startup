package com.innoprog.android.di

import com.innoprog.android.db.RoomDBModule
import com.innoprog.android.network.data.ApiModule
import com.innoprog.android.network.data.NetworkModule
import com.innoprog.android.network.domain.ApiInteractor
import dagger.Component

@Component(
    modules = [NetworkModule::class, ApiModule::class, RoomDBModule::class]
)
interface AppComponent : DIComponent {
    val apiInteractor: ApiInteractor
}

interface DIComponent

// Эта сущность нужна чтобы хранить AppComponent в памяти и подсовывать в наши ScreenComponent'ы
object AppComponentHolder : ComponentHolder<AppComponent>() {
    override fun buildComponent(): AppComponent = DaggerAppComponent.builder().build()
}
