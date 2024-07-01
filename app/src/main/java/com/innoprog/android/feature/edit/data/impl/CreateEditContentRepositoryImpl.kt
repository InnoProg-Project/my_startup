package com.innoprog.android.feature.edit.data.impl

import com.innoprog.android.feature.edit.data.CreateEditContentRepository
import com.innoprog.android.feature.edit.data.EditContentNetworkClient
import com.innoprog.android.feature.edit.data.dto.EditContentAdapter
import com.innoprog.android.feature.edit.data.dto.ProjectResponse
import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.model.MediaAttachmentsModel
import com.innoprog.android.feature.edit.domain.model.ProjectModel
import com.innoprog.android.feature.edit.domain.model.PublicationModel
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateEditContentRepositoryImpl @Inject constructor(
    private val networkClient: EditContentNetworkClient,
    private val adapter: EditContentAdapter
) : CreateEditContentRepository {

    private val mediaListOfPath = mutableListOf<String>()

    override suspend fun addMediaToListAttachments(path: String): Resource<MediaAttachmentsModel> {
        return withContext(Dispatchers.Default) {
            when (val fileFormat = formatFileVerification(path)) {
                FormatFile.VIDEO, FormatFile.IMAGE -> {
                    if (isFileSizeCorrect(path, fileFormat)) {
                        mediaListOfPath.add(path)
                        Resource.Success(MediaAttachmentsModel(mediaListOfPath))
                    } else {
                        Resource.Error(ErrorType.INVALID_FILE_SIZE)
                    }
                }


                FormatFile.INVALID -> Resource.Error(ErrorType.INVALID_FILE_FORMAT)
            }
        }
    }

    override suspend fun deleteMediaFromListAttachments(path: String): Resource<MediaAttachmentsModel> {
        return withContext(Dispatchers.Default) {
            if (mediaListOfPath.contains(path)) {
                mediaListOfPath.remove(path)
                Resource.Success(MediaAttachmentsModel(mediaListOfPath))
            } else {
                Resource.Error(ErrorType.BAD_REQUEST)
            }
        }
    }

    override suspend fun getMediaAttachments(): Resource<MediaAttachmentsModel> {
        return withContext(Dispatchers.Default) {
            if (mediaListOfPath.isNotEmpty()) {
                Resource.Success(MediaAttachmentsModel(mediaListOfPath))
            } else {
                Resource.Error(ErrorType.BAD_REQUEST)
            }
        }
    }

    override suspend fun createIdea(ideaModel: IdeaModel) {

    }

    override suspend fun createPublication(publicationModel: PublicationModel) {

    }

    override fun getProjectById(id: String): Flow<Resource<ProjectModel>> {
        return flow {
            val response = networkClient.getProjectById(id)

            when (response.resultCode) {
                ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                    emit(Resource.Error(ErrorType.NO_CONNECTION))
                }

                ApiConstants.SUCCESS_CODE -> {
                    with(response as ProjectResponse) {
                        val result = adapter.mapToProjectModel(this)
                        emit(Resource.Success(result))
                    }
                }

                else -> {
                    emit(Resource.Error(ErrorType.BAD_REQUEST))
                }
            }
        }
    }

    override suspend fun updatePublication(publicationModel: PublicationModel) {

    }

    override fun getPublicationById(id: String): Flow<PublicationModel> {
        return flow { }
    }

    private fun formatFileVerification(filePath: String): FormatFile {
        val extension = filePath.substringAfterLast('.', "")

        return when (extension.lowercase()) {
            PNG, JPG, JPEG -> FormatFile.IMAGE
            MOV, MP4 -> FormatFile.VIDEO
            else -> FormatFile.INVALID
        }
    }

    private fun isFileSizeCorrect(filePath: String, formatFile: FormatFile): Boolean {
        val file = File(filePath)
        if (!file.exists()) {
            return false
        }

        return when (formatFile) {
            FormatFile.IMAGE -> {
                file.length() < MAX_IMAGE_SIZE
            }

            FormatFile.VIDEO -> {
                file.length() < MAX_VIDEO_SIZE
            }

            else -> false
        }

    }

    companion object {

        enum class FormatFile {
            IMAGE,
            VIDEO,
            INVALID
        }

        const val PNG = "png"
        const val JPG = "jpg"
        const val JPEG = "jpeg"
        const val MP4 = "mp4"
        const val MOV = "mov"
        const val MAX_IMAGE_SIZE = 25165824
        const val MAX_VIDEO_SIZE = 83886080
    }
}
