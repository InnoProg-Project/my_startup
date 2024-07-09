package com.innoprog.android.network.data

import com.innoprog.android.network.data.cryptography.AESHelperImpl
import com.innoprog.android.network.data.cryptography.RSAHelperImpl
import com.innoprog.android.network.data.cryptography.RootCheckerImpl
import com.innoprog.android.network.domain.AuthorizationDataRepository
import com.innoprog.android.network.domain.cryptography.AESHelper
import com.innoprog.android.network.domain.cryptography.RSAHelper
import com.innoprog.android.network.domain.cryptography.RootChecker
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(
        authorizationDataRepository: AuthorizationDataRepository
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authorizationInterceptor = { chain: Interceptor.Chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("X-Authorization", authorizationDataRepository.execute())
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .callTimeout(ApiConstants.CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(ApiConstants.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthorisationDataRepository(repository: AuthorizationDataRepositoryImpl): AuthorizationDataRepository {
        return repository
    }

    @Provides
    fun provideAESHelper(aesHelper: AESHelperImpl): AESHelper {
        return aesHelper
    }

    @Provides
    fun provideRSAHelper(rsaHelper: RSAHelperImpl): RSAHelper {
        return rsaHelper
    }

    @Provides
    fun provideRootChecker(rootChecker: RootCheckerImpl): RootChecker {
        return rootChecker
    }
}
