package com.innoprog.android.feature.training.courseInformation.domain.useCase

import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.domain.model.Attachments
import com.innoprog.android.feature.training.courseInformation.domain.model.AttachmentsType
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationDocumentModel
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationImageModel
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationVideoModel
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseInformationUseCaseImpl @Inject constructor(
    private val repository: CourseInformationRepository
) : GetCourseInformationUseCase {

    override fun execute(courseId: String): Flow<Resource<CourseInformation?>> {
        return repository.getCourseInformation(courseId)
    }

    override fun getVideo(listAttachments: List<Attachments>?): List<CourseInformationVideoModel> {
        val result = ArrayList<CourseInformationVideoModel>()
        listAttachments?.forEach { attachments ->
            if (attachments.type == AttachmentsType.VIDEO.value) {
                result.addAll(
                    listAttachments.map {
                        CourseInformationVideoModel(
                            videoId = it.id,
                            videoURL = it.filePath,
                        )

                    },
                )
            }
        }
        return result.toList()
    }

    override fun getImage(listAttachments: List<Attachments>?): List<CourseInformationImageModel> {
        val result = ArrayList<CourseInformationImageModel>()
        listAttachments?.forEach { attachments ->
            if (attachments.type == AttachmentsType.IMAGE.value) {
                result.addAll(
                    listAttachments.map {
                        CourseInformationImageModel(
                            imageId = it.id,
                            imageURL = it.filePath,
                        )

                    },
                )
            }
        }
        return result.toList()
    }

    override fun getDocument(listAttachments: List<Attachments>?): List<CourseInformationDocumentModel> {
        val result = ArrayList<CourseInformationDocumentModel>()
        listAttachments?.forEach { attachments ->
            if (attachments.type == AttachmentsType.DOCUMENT.value) {
                result.addAll(
                    listAttachments.map {
                        CourseInformationDocumentModel(
                            documentId = it.id,
                            documentURL = it.filePath,
                        )

                    },
                )
            }
        }
        return result.toList()
    }
}
