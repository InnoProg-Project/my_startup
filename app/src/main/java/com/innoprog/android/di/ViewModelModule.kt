package com.innoprog.android.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.base.ViewModelSample
import com.innoprog.android.feature.auth.presentation.viewmodel.AuthorizationViewModel
import com.innoprog.android.feature.auth.presentation.viewmodel.CodeEntryViewModel
import com.innoprog.android.feature.auth.presentation.viewmodel.NewPasswordViewModel
import com.innoprog.android.feature.auth.presentation.viewmodel.PasswordRecoveryViewModel
import com.innoprog.android.feature.auth.presentation.viewmodel.RegistrationViewModel
import com.innoprog.android.feature.feed.presentation.FeedViewModel
import com.innoprog.android.feature.profile.presentation.ProfileViewModel
import com.innoprog.android.feature.projects.presentation.ProjectsViewModel
import com.innoprog.android.feature.training.presentation.TrainingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ViewModelSample::class)
    @Binds
    fun bindSampleViewModel(impl: ViewModelSample): ViewModel

    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    @Binds
    fun bindAuthorizationViewModel(impl: AuthorizationViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CodeEntryViewModel::class)
    @Binds
    fun bindCodeEntryViewModel(impl: CodeEntryViewModel): ViewModel

    @IntoMap
    @ViewModelKey(NewPasswordViewModel::class)
    @Binds
    fun bindNewPasswordViewModel(impl: NewPasswordViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PasswordRecoveryViewModel::class)
    @Binds
    fun bindPasswordRecoveryViewModel(impl: PasswordRecoveryViewModel): ViewModel

    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    @Binds
    fun bindRegistrationViewModel(impl: RegistrationViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    @Binds
    fun bindFeedViewModel(impl: FeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(impl: ProfileViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProjectsViewModel::class)
    @Binds
    fun bindProjectsViewModel(impl: ProjectsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    @Binds
    fun bindTrainingViewModel(impl: TrainingViewModel): ViewModel
}
