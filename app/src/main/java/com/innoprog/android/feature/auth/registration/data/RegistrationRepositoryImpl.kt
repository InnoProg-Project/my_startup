package com.innoprog.android.feature.auth.registration.data

import android.util.Log
import com.innoprog.android.feature.auth.registration.domain.RegistrationRepository
import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel
import com.innoprog.android.network.data.ApiConstants.BAD_REQUEST_CODE
import com.innoprog.android.network.data.ApiConstants.SUCCESS_CODE
import retrofit2.HttpException
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(private val api: RegistrationApi) :
    RegistrationRepository {

    override suspend fun registration(
        registrationValue: RegistrationModel
    ): Pair<Boolean, String?> {
        try {
            val response = api.setRegistrationData(mapToRegistrationRequest(registrationValue))
            Log.d("myRegistration", "my $response")
            return when (response.resultCode) {
                BAD_REQUEST_CODE -> {
                    Pair(false, "error")
                }

                SUCCESS_CODE -> {
                    Pair(true, null)
                }

                else -> {
                    Pair(false, "error")
                }
            }
        } catch (e: HttpException) {
            Log.e("myRegistration", " error: $e")
            return Pair(false, "error")
        }
    }

    private fun mapToRegistrationRequest(value: RegistrationModel): RegistrationBody {
        return RegistrationBody(
            value.userName!!,
            value.email!!,
            value.phone,
            value.password!!,
            value.about,
            value.language
        )
    }
}
