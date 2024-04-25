package com.innoprog.android.di

import android.app.Application
import android.content.Context
import com.innoprog.android.db.RoomDB
import com.innoprog.android.db.RoomDBModule
import com.innoprog.android.network.data.ApiModule
import com.innoprog.android.network.data.NetworkModule
import com.innoprog.android.network.domain.ApiInteractor
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        ApiModule::class,
        RoomDBModule::class,
        ContextModule::class
    ]
)
interface AppComponent : DIComponent {

    val apiInteractor: ApiInteractor
    val room: RoomDB
    val context: Context
    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(app: Application): Builder
    }
}

interface DIComponent

// Эта сущность нужна чтобы хранить AppComponent в памяти и подсовывать в наши ScreenComponent'ы
object AppComponentHolder : DataBasedComponentHolder<AppComponent, Application>() {
    override val mode: ComponentHolderMode = ComponentHolderMode.GLOBAL_SINGLETON
    override fun buildComponent(data: Application): AppComponent = DaggerAppComponent.builder().app(data).build()
}
