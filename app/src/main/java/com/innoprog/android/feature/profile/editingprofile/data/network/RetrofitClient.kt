package com.innoprog.android.feature.profile.editingprofile.data.network

import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val service: EditingProfileApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {

        var response = Response()

        return withContext(Dispatchers.IO) {
            try {
                response = when (dto) {

                    is Request.PutProfile -> {
                        val body = EditingProfileBody(name = "actual_name", about = "actual_about")
                        service.editProfile(body = body)
                    }

                    is Request.PutProfileCompany -> {
                        val body = EditingProfileCompanyBody(
                            name = "actual_name",
                            url = "actual_url",
                            role = "actual_role"
                        )
                        service.editProfileCompany(body = body)
                    }

                    else -> {
                        throw IllegalArgumentException("Unsupported request type")
                    }
                }
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (exception: HttpException) {
                response.apply { resultCode = exception.code() }
            }
        }
    }
}