package com.innoprog.android.feature.training.courseInformation.data

import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.presentation.model.CourseInformationDocumentModel
import com.innoprog.android.feature.training.courseInformation.presentation.model.CourseInformationModel
import com.innoprog.android.feature.training.courseInformation.presentation.model.CourseInformationVideoModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseInformationRepositoryImpl @Inject constructor() : CourseInformationRepository {

    private val video = CourseInformationVideoModel(COURSE_VIDEO_URL, COURSE_VIDEO_DESCRIPTION)
    private val document = CourseInformationDocumentModel(COURSE_DOCUMENT_URL, COURSE_DOCUMENT_TITLE)

    private val courseModel = CourseInformationModel(
        COURSE_ID,
        COURSE_LOGO_URL,
        COURSE_ID_TITLE,
        COURSE_DESCRIPTION,
        COURSE_AVATAR_URL,
        COURSE_NAME,
        COURSE_AUTHOR_POSITION,
        COURSE_DATE,
        COURSE_DIRECTION,
        listOf(video, video),
        listOf(document, document),
    )

    override fun getCourseInformation(courseId: Int): Flow<Pair<CourseInformationModel?, ErrorStatus?>> = flow {
        emit(Pair(courseModel, null))
    }

    companion object {

        const val COURSE_ID = 123
        const val COURSE_LOGO_URL =
            "https://s3-alpha-sig.figma.com/img/204c/9620/c0610d5f7c4375333f671aa481fd5913?Expires=1712534400&Key" +
                "-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=KKrsA83EgNNT~NKXXk9w2i6I72VU36gajM8TZKvm2nlbDLzAIPmG" +
                "JIWHO-lEGQfcQKR90G6nZ6RLdjAF1YnndIEMjTkRQGtymiheLeph~~ZB1p4uXioGjzgmcqZHczQAEnSQsRq~DQLn5dkP" +
                "EFlVxJ6aPx8ZLX1sOCUbNZvCiEkJ2dLDz~TQQYMWzYM-u4Q4ZhsN1AfRLacLdGQ1JJYoguv3JOJlTADz9FmeNP0JzeYk" +
                "4~p8opEi6rqNfRNXXpBon~9QL42fZ1e2DfnaQvda4JhT5qtJZ6Gks0eHczR1xRsNZayrQDIyBH0o-JnfeoJ6KmOb~76M" +
                "feuL5OtQP7AYrg__"
        const val COURSE_ID_TITLE = "Интеграция сервисов"
        const val COURSE_DESCRIPTION =
            "Небольшой курс о том, как интегрировать сервисы в ваше приложение или сайт без участия разработчика"
        const val COURSE_AVATAR_URL =
            "https://wallpapers4screen.com/Uploads/27-1-2016/18417/cat-tiger-white-cat-cats-photo.jpg"
        const val COURSE_NAME = "Унтура Михаил"
        const val COURSE_AUTHOR_POSITION = "СЕО в Мой Стартап"
        const val COURSE_DATE = "20 мая"
        const val COURSE_DIRECTION = "Управление"
        const val COURSE_VIDEO_URL = "https://www.youtube.com/watch?v=KbUBfXEju3c&ab_channel=MemeMystery"
        const val COURSE_VIDEO_DESCRIPTION =
            "This Song was Sung by the Chilean Singer \"Christel\" in the popular Chilean Television Show \"Rojo " +
                "Fama Contrafama\" is now getting really viral on social media these days. Enjoy the full song!"
        const val COURSE_DOCUMENT_URL =
            "https://vk.com/doc26879026_667397256?hash=rzBA9TMriYBMiAeZ897JFlgN16zDCYgMjzjvEjCNXrT&dl=2sJzIf4MVRn" +
                "SPefLu4nOf7b7Ufla1oCvEdEypGPuM2s"
        const val COURSE_DOCUMENT_TITLE = "Программирование на Kotlin для Android"
    }
}
