package com.innoprog.android.feature.projects.projectsScreen.data.network

import android.content.Context
import com.google.gson.JsonParseException
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.DocumentModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.ImageModel
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileDao
import com.innoprog.android.feature.projects.data.dto.ProjectDto
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.isInternetReachable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class ProjectListNetworkClientImpl @Inject constructor(
    private val apiService: ProjectApiService,
    private val context: Context,
    private val profileDao: ProfileDao
) : ProjectListNetworkClient {

    override suspend fun getProjectList(): Response {
        if (context.isInternetReachable().not()) {
            return createErrorResponse(ApiConstants.NO_INTERNET_CONNECTION_CODE)
        }

        return withContext(Dispatchers.IO) {
            executeApiCall()
        }
    }

    private suspend fun executeApiCall(): Response {
        val q = listOf<ProjectDto>(
            ProjectDto(
                id = UUID.randomUUID(),
                name = anyProjectDetails.name,
                shortDescription = anyProjectDetails.shortDescription,
                description = anyProjectDetails.description,
                logoFilePath = anyProjectDetails.projectLogoFilePath,
                publicationsCount = 24,
                area = anyProjectDetails.area,
                financingStage = anyProjectDetails.financingStage,
                deadline = Date(),
                siteUrls = "",
                documentUrls = emptyList(),
                projectAttachments = emptyList()
            )
        )

        return ProjectListResponse(
            q
        ).apply {
            resultCode = ApiConstants.SUCCESS_CODE
        }
        return try {
            val userId = getUserId()
            val response = apiService.getProjectList(
                userId,
                null,
                PROJECTS_AMOUNT_PER_PAGE_50
            )



            if (response.isSuccessful) {
                ProjectListResponse(result = response.body() ?: emptyList()).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } else {
                createErrorResponse(response.code())
            }
        } catch (exception: HttpException) {
            logAndCreateErrorResponse(exception)
        } catch (exception: IOException) {
            logAndCreateErrorResponse(exception)
        } catch (exception: JsonParseException) {
            logAndCreateErrorResponse(exception)
        } catch (exception: SocketTimeoutException) {
            logAndCreateErrorResponse(exception)
        }
    }

    private suspend fun getUserId(): String {
        val profile = profileDao.getProfile()
        return profile.userId
    }

    private fun createErrorResponse(code: Int): Response {
        return Response().apply {
            resultCode = code
        }
    }

    private fun logAndCreateErrorResponse(exception: Throwable): Response {
        val errorCode = when (exception) {
            is HttpException -> exception.code()
            is IOException -> ApiConstants.NO_INTERNET_CONNECTION_CODE
            is JsonParseException -> ApiConstants.BAD_REQUEST_CODE
            is SocketTimeoutException -> ApiConstants.NO_INTERNET_CONNECTION_CODE
            else -> ApiConstants.INTERNAL_SERVER_ERROR
        }
        return createErrorResponse(errorCode)
    }

    private companion object {
        const val PROJECTS_AMOUNT_PER_PAGE_50 = 50

        private val image = ImageModel(
            id = "1",
            filePath = "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
                    "vector-digital-transformation-concept_53876-112222.jpg",
            type = "image"
        )

        private val document = DocumentModel(
            documentURL = "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
                    "vector-digital-transformation-concept_53876-112222.jpg",
            documentTitle = "Отчёт по инвестициям за I квартал 2024.docx"
        )

        private val linkToWeb = "mystartup.com"
        private val linkToApp = "https://apps.apple.com/ru/appmystartup"
        private val linkToSocNet = "vk.com/mystartup"

        private val anyProjectDetails = AnyProjectDetailsModel(
            id = "555",
            images = listOf(image, image, image, image),
            name = "Мой Стартап",
            projectLogoFilePath = "https://img.freepik.com/free-vector/ai-technology-microchip-" +
                    "background-vector-digital-transformation-concept_53876-112222.jpg",
            area = "Искусственный интеллект",
            shortDescription = "Помощник в воспитании детей",
            description = "Образовательный уровень родителей зачастую влияет на будущее детей больше, " +
                    "чем их успеваемость в школе. Сотрудники Института образования НИУ ВШЭ выяснили, " +
                    "какую роль играет этот фактор в том выборе, который школьник делает после 9 и 11 " +
                    "классов. Как следует из результатов исследования, далеко не всегда этот выбор " +
                    "продиктован академической успеваемостью. Семья с высоким уровнем образования  " +
                    "помогает школьнику выбрать такие стратегии, которые позволяют получить хорошее " +
                    "образование даже при невысокой успеваемости.",
            documents = listOf(document, document, document),
            financingStage = "Проекту необходимо 8 млн ₽ на развитие первого этапа " +
                    "(Stage 1 в нашей презентации). Срок реализации указан для stage 1",
            deadline = "12 марта 2026",
            siteUrls = listOf(linkToWeb, linkToApp, linkToSocNet)
        )
    }
}