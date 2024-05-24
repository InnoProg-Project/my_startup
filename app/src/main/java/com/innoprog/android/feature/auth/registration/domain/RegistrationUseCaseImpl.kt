package com.innoprog.android.feature.auth.registration.domain

import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationUseCaseImpl @Inject constructor(private val repository: RegistrationRepository) :
    RegistrationUseCase {
    override fun registration(
        data: RegistrationModel
    ): Flow<Pair<Boolean, String?>> = flow {
        val firstChecking =
            (data.userName?.length ?: ZERO) in MIN_NAME..MAX_NAME &&
                    (data.email?.length ?: ZERO) in MIN_EMAIL..MAX_EMAIL &&
                    (data.password?.length ?: ZERO) in MIN_PASSWORD..MAX_PASSWORD
        if (firstChecking) emit(repository.registration(data)) else emit(Pair(false, "error"))
    }

    companion object {
        const val ZERO = 0
        const val MIN_NAME = 2
        const val MAX_NAME = 60
        const val MIN_EMAIL = 3
        const val MAX_EMAIL = 255
        const val MIN_PASSWORD = 2
        const val MAX_PASSWORD = 60

    }
}
