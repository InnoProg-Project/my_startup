package com.innoprog.android.feature.edit.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.edit.data.CreateEditContentRepository
import com.innoprog.android.feature.edit.data.EditContentNetworkClient
import com.innoprog.android.feature.edit.data.dto.EditContentAdapter
import com.innoprog.android.feature.edit.data.impl.CreateEditContentRepositoryImpl
import com.innoprog.android.feature.edit.data.impl.EditContentNetworkClientImpl
import com.innoprog.android.feature.edit.data.network.EditContentApi
import com.innoprog.android.feature.edit.domain.useCase.CreateIdeaUseCase
import com.innoprog.android.feature.edit.domain.useCase.CreatePublishUseCase
import com.innoprog.android.feature.edit.domain.useCase.EditePublishUseCase
import com.innoprog.android.feature.edit.domain.useCase.impl.CreateIdeaUseCaseImpl
import com.innoprog.android.feature.edit.domain.useCase.impl.CreatePublishUseCaseImpl
import com.innoprog.android.feature.edit.domain.useCase.impl.EditePublishUseCaseImpl
import com.innoprog.android.feature.edit.presentation.CreateEditContentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [CreateEditContentModule.EditContentApiModule::class])
interface CreateEditContentModule {
    @IntoMap
    @ViewModelKey(CreateEditContentViewModel::class)
    @Binds
    fun bindCreateEditContentViewModel(impl: CreateEditContentViewModel): ViewModel

    @Binds
    fun bindCreateIdeaUseCase(impl: CreateIdeaUseCaseImpl): CreateIdeaUseCase

    @Binds
    fun bindCreatePublishUseCase(impl: CreatePublishUseCaseImpl): CreatePublishUseCase

    @Binds
    fun bindEditPublishUseCase(impl: EditePublishUseCaseImpl): EditePublishUseCase

    @Binds
    fun bindCreateEditContentRepository(impl: CreateEditContentRepositoryImpl): CreateEditContentRepository

    @Binds
    fun bindEditContentNetworkClient(impl: EditContentNetworkClientImpl): EditContentNetworkClient

    @Binds
    fun bindEditContentAdapter(impl: EditContentAdapter): EditContentAdapter

    @Module
    class EditContentApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): EditContentApi {
            return retrofit.create(EditContentApi::class.java)
        }
    }
}
