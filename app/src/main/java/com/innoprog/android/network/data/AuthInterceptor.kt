package com.innoprog.android.network.data

import com.innoprog.android.network.data.ApiConstants.PASSWORD
import com.innoprog.android.network.data.ApiConstants.USER_NAME
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Base64

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authData = "$USER_NAME:$PASSWORD"

        val request = chain.request().newBuilder()
            .addHeader(
                "X-Authorization",
                "Basic " + Base64.getEncoder().encodeToString(authData.toByteArray())
            )
            .build()
        return chain.proceed(request)
    }
}
