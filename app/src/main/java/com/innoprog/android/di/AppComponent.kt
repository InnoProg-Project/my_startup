package com.innoprog.android.di

import com.innoprog.android.network.data.ApiModule
import com.innoprog.android.network.data.NetworkModule
import dagger.Component

@Component(
    modules = [NetworkModule::class, ApiModule::class]
)
interface AppComponent : DIComponent

interface DIComponent

// Эта сущность нужна чтобы хранить AppComponent в памяти и подсовывать в наши ScreenComponent'ы
object AppComponentHolder: ComponentHolder<AppComponent>() {
    override fun buildComponent(): AppComponent = DaggerAppComponent.builder().build()
}
