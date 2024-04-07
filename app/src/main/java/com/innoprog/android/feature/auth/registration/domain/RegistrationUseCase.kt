package com.innoprog.android.feature.auth.registration.domain

import kotlinx.coroutines.flow.Flow

interface RegistrationUseCase {
    fun registration(login: String, email: String, phone:String?, password: String): Flow<Pair<Boolean, String?>>
}
