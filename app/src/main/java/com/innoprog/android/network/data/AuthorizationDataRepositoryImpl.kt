package com.innoprog.android.network.data

import com.innoprog.android.network.domain.AuthorizationDataRepository
import javax.inject.Inject

class AuthorizationDataRepositoryImpl @Inject constructor() : AuthorizationDataRepository {
    override fun execute(): String {
        return AuthHeader.data
    }

    override fun setData(data: String) {
        AuthHeader.data = data
    }
}
