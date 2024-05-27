package com.innoprog.android.feature.auth.registration.data

import android.util.Log
import com.innoprog.android.feature.auth.registration.domain.RegistrationRepository
import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(private val api: RegistrationApi) :
    RegistrationRepository {

    override fun registration(
        registrationValue: RegistrationModel
    ): Flow<Pair<Boolean, String?>> = flow {
        try {
            val response = api.setRegistrationData(mapToRegistrationRequest(registrationValue))
            Log.d("myRegistration", "my $response")
            when (response.resultCode) {
                BAD_REQUEST_CODE -> {
                    emit(Pair(false, "error"))
                }

                SUCCESS_CODE -> {
                    Pair(true, null)
                }

                else -> {
                    emit(Pair(false, "error"))
                }
            }
        } catch (e: HttpException) {
            Log.e("myRegistration", " error: $e")
            emit(Pair(false, "error"))
        } catch (e: SocketTimeoutException) {
            Log.e("myRegistration", " error: $e")
            emit(Pair(false, "error"))
        }
    }

    private fun mapToRegistrationRequest(value: RegistrationModel): RegistrationBody {
        return RegistrationBody(
            value.userName!!,
            value.email!!,
            value.phone,
            value.password!!
        )
    }

    companion object {
        const val SUCCESS_CODE = 201
        const val BAD_REQUEST_CODE = 401
    }
}
