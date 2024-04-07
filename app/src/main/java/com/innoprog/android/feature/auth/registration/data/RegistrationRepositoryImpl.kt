package com.innoprog.android.feature.auth.registration.data

import com.innoprog.android.feature.auth.registration.domain.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor() : RegistrationRepository {
    override fun registration(
        login: String,
        email: String,
        phone: String?,
        password: String
    ): Flow<Pair<Boolean, String?>> = flow {
        val response = 200
        when (response) {
            -1 -> {
                emit(Pair(false, "error"))
            }

            200 -> {
                emit(Pair(true, null))
            }

            else -> {
                emit(Pair(false, "error"))
            }
        }
    }
}
