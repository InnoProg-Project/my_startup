package com.innoprog.android.feature.training.courseInformation.domain.useCase

import com.innoprog.android.feature.training.courseInformation.domain.model.Attachments
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationDocumentModel
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationImageModel
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationVideoModel
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetCourseInformationUseCase {

    fun execute(courseId: String): Flow<Resource<CourseInformation?>>
    fun getVideo(listAttachments: List<Attachments>?): List<CourseInformationVideoModel>
    fun getImage(listAttachments: List<Attachments>?): List<CourseInformationImageModel>
    fun getDocument(listAttachments: List<Attachments>?): List<CourseInformationDocumentModel>
}
