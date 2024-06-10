package com.innoprog.android.network.data

import android.util.Base64
import com.innoprog.android.network.data.ApiConstants.PASSWORD
import com.innoprog.android.network.data.ApiConstants.USER_NAME
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Basic ${
                    Base64.encodeToString(
                        "$USER_NAME:$PASSWORD".toByteArray(),
                        Base64.NO_WRAP
                    )
                }"
            )
            .build()
        return chain.proceed(request)
    }
}
