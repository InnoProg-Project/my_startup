package com.innoprog.android.feature.projects.projectsScreen.data.repository

import android.util.Log
import com.google.gson.JsonParseException
import com.innoprog.android.feature.projects.data.dto.ProjectDto
import com.innoprog.android.feature.projects.projectsScreen.data.network.ProjectListNetworkClient
import com.innoprog.android.feature.projects.projectsScreen.data.network.ProjectListResponse
import com.innoprog.android.feature.projects.projectsScreen.domain.api.ProjectRepository
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorHandler
import com.innoprog.android.util.ErrorHandlerImpl
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val networkClient: ProjectListNetworkClient
) : ProjectRepository {
    private val errorHandler: ErrorHandler = ErrorHandlerImpl()
    override suspend fun getProjectList(): Resource<List<ProjectDto>> {
        return try {
            val response = networkClient.getProjectList()
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    val projects = (response as? ProjectListResponse)?.result
                    Resource.Success(projects ?: emptyList())
                }

                else -> errorHandler.handleErrorCode(response.resultCode)
            }
        } catch (e: HttpException) {
            Log.e(ERROR_TAG, e.toString())
            errorHandler.handleHttpException(e)
        } catch (e: IOException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        } catch (e: JsonParseException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.UNEXPECTED)
        } catch (e: SocketTimeoutException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        }
    }

    private companion object {
        const val ERROR_TAG = "ProjectRepository"
    }
}
