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
        val response = mok_result
        when (response) {
            BAD_REQUEST -> {
                emit(Pair(false, "error"))
            }

            GOOD_REQUEST -> {
                emit(Pair(true, null))
            }

            else -> {
                emit(Pair(false, "error"))
            }
        }
    }

    companion object {
        const val mok_result = 200
        const val BAD_REQUEST = -1
        const val GOOD_REQUEST = 200
    }
}
