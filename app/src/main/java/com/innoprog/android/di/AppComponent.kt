package com.innoprog.android.di

import com.innoprog.android.base.MainFragment
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}
