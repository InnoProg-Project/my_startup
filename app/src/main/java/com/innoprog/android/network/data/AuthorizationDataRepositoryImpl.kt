package com.innoprog.android.network.data

import com.innoprog.android.network.domain.AuthorizationDataRepository
import java.util.Base64
import javax.inject.Inject

class AuthorizationDataRepositoryImpl @Inject constructor() : AuthorizationDataRepository {
    override fun execute(): String {
        return AuthHeader.data
    }

    override fun setData(login: String, password: String) {
        val authData = "$login:$password"
        AuthHeader.data = "Basic " + Base64.getEncoder().encodeToString(authData.toByteArray())
    }
}
