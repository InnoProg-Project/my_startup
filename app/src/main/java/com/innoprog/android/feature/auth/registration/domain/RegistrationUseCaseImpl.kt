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
            (data.userName?.length ?: 0) in 2..60 &&
                    (data.email?.length ?: 0) in 3..255 &&
                    (data.password?.length ?: 0) in 6..60 &&
                    (data.language?.length ?: 0) <= 512
        if (firstChecking) emit(repository.registration(data)) else emit(Pair(false, "error"))
    }
}
