package com.innoprog.android.feature.auth.registration.data

import com.innoprog.android.feature.auth.registration.domain.RegistrationRepository
import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor() : RegistrationRepository {

    override fun registration(
        registrationValue: RegistrationModel
    ): Flow<Pair<Boolean, String?>> = flow {
        val response = validate(mapToRegistrationRequest(registrationValue))
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

    private fun mapToRegistrationRequest(value: RegistrationModel): RegistrationRequest {
        return RegistrationRequest(
            value.userName,
            value.phone,
            value.email,
            value.password
        )
    }

    private fun validate(value: RegistrationRequest): Int {
        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(value.email)
                .matches()
        ) mok_result else BAD_REQUEST
    }

    companion object {
        const val mok_result = 200
        const val BAD_REQUEST = -1
        const val GOOD_REQUEST = 200
    }
}
