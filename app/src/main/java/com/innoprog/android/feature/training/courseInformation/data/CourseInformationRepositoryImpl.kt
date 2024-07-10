package com.innoprog.android.feature.training.courseInformation.data

import android.util.Log
import com.innoprog.android.feature.training.courseInformation.data.network.AttachmentsDto
import com.innoprog.android.feature.training.courseInformation.data.network.CourseInformationDto
import com.innoprog.android.feature.training.courseInformation.data.network.CourseInformationNetworkClient
import com.innoprog.android.feature.training.courseInformation.data.network.CourseInformationResponse
import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationAttachmentsType
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorHandler
import com.innoprog.android.util.ErrorHandlerImpl
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CourseInformationRepositoryImpl @Inject constructor(
    private val courseInformationNetworkClient: CourseInformationNetworkClient
) : CourseInformationRepository {
    private val errorHandler: ErrorHandler = ErrorHandlerImpl()

    override fun getCourseInformation(courseId: String): Flow<Resource<CourseInformation>> = flow {
        val response = courseInformationNetworkClient.getCourseInformation(courseId)
        runCatching {
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    with(response as CourseInformationResponse) {
                        val data = mapToCourseInformation(results)
                        emit(Resource.Success(data))
                    }
                }

                else -> {
                    emit(errorHandler.handleErrorCode(response.resultCode))
                }
            }
        }.onFailure { exception ->
            Log.v(ERROR_TAG, "mapping error -> ${exception.localizedMessage}")
            emit(Resource.Error(ErrorType.UNKNOWN_ERROR))
        }
    }

    private fun mapToCourseInformation(data: CourseInformationDto): CourseInformation {
        return CourseInformation(
            id = data.id,
            title = data.title,
            direction = data.direction ?: "",
            description = data.description ?: "",
            authorName = data.author,
            createdDate = formatDate(data.publishedAt),
            usefulLinks = data.usefulLinks,
            videoList = splitAttachments(
                data.attachments,
                CourseInformationAttachmentsType.VIDEO.value
            ),
            documentList = splitAttachments(
                data.attachments,
                CourseInformationAttachmentsType.DOCUMENT.value
            ),
            imageList = splitAttachments(
                data.attachments,
                CourseInformationAttachmentsType.IMAGE.value
            )
        )
    }

    private fun formatDate(publishedAt: String): String {
        var resultDate = ""
        val createdDate = publishedAt.substring(0, publishedAt.indexOf('T'))
        runCatching {
            val date = LocalDate.parse(createdDate, DateTimeFormatter.ISO_DATE)
            resultDate = date.format(DateTimeFormatter.ofPattern("dd MMM"))
        }
        return resultDate
    }

    private fun splitAttachments(
        listAttachments: List<AttachmentsDto>?,
        attachmentsType: String
    ): List<String> {
        val result = mutableListOf<String>()
        listAttachments?.forEach { attachments ->
            if (attachments.type == attachmentsType) {
                result.add(attachments.filePath)
            }
        }
        return result.toList()
    }

    private companion object {
        val ERROR_TAG = CourseInformationRepository::class.simpleName
    }
}
