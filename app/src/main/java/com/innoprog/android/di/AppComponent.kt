package com.innoprog.android.di

import com.innoprog.android.base.MainFragment
import com.innoprog.android.base.ViewModelFactory
import com.innoprog.android.feature.auth.presentation.fragment.AuthorizationFragment
import com.innoprog.android.feature.auth.presentation.fragment.CodeEntryFragment
import com.innoprog.android.feature.auth.presentation.fragment.NewPasswordFragment
import com.innoprog.android.feature.auth.presentation.fragment.PasswordRecoveryFragment
import com.innoprog.android.feature.auth.presentation.fragment.RegistrationFragment
import com.innoprog.android.feature.feed.presentation.FeedFragment
import com.innoprog.android.feature.profile.presentation.ProfileFragment
import com.innoprog.android.feature.projects.presentation.ProjectsFragment
import com.innoprog.android.feature.training.presentation.TrainingFragment
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class])
interface AppComponent {

    val viewModelFactory: ViewModelFactory

    fun inject(fragment: MainFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: FeedFragment)
    fun inject(fragment: CodeEntryFragment)
    fun inject(fragment: NewPasswordFragment)
    fun inject(fragment: PasswordRecoveryFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: ProjectsFragment)
    fun inject(fragment: TrainingFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}
