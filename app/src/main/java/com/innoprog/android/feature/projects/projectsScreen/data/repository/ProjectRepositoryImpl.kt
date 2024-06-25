package com.innoprog.android.feature.projects.projectsScreen.data.repository

import com.innoprog.android.feature.projects.data.dto.ProjectDto
import com.innoprog.android.feature.projects.domain.api.ProjectRepository
import com.innoprog.android.feature.projects.projectsScreen.data.network.ProjectListNetworkClient
import com.innoprog.android.feature.projects.projectsScreen.data.network.ProjectListResponse
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorHandler
import com.innoprog.android.util.ErrorHandlerImpl
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import retrofit2.HttpException
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

                ApiConstants.NO_INTERNET_CONNECTION_CODE -> Resource.Error(ErrorType.NO_CONNECTION)
                else -> errorHandler.handleErrorCode(response.resultCode)
            }
        } catch (e: HttpException) {
            errorHandler.handleHttpException(e)
        } catch (e: Exception) {
            Resource.Error(ErrorType.UNKNOWN_ERROR)
        }
    }
}
