package com.innoprog.android.feature.training.course_information.data

import com.innoprog.android.feature.training.course_information.domain.CourseInformationRepository
import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationDocumentModel
import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationModel
import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationVideoModel
import com.innoprog.android.feature.training.training_list.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseInformationRepositoryImpl @Inject constructor() : CourseInformationRepository {

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
        listOf(CourseInformationVideoModel(COURSE_VIDEO_URL, COURSE_VIDEO_DESCRIPTION)),
        listOf(CourseInformationDocumentModel(COURSE_DOCUMENT_URL, COURSE_DOCUMENT_TITLE))
    )

    override fun getCourseInformation(courseId: Int): Flow<Pair<CourseInformationModel?, ErrorStatus?>> = flow {
        emit(Pair(courseModel, null))
    }

    companion object {

        const val COURSE_ID = 123
        const val COURSE_LOGO_URL =
            "https://s3-alpha-sig.figma.com/img/3e0d/9498/c37b099866399c2f0734158e9b168c04?Expires=1712534400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=TUqaPq4e7wFQN0H70KdwNiKz668MyUNuF2Goowueu0VvCLbGwcC6FAedW0qEt83W1kQVjBEPde4k6EPiJfWZIhwTljsPTrdllyD8LnO1R~sQg2qaFS-~14liv924YXwk2aN6D~vZ0wxRyxsydVw03lDvFK2nSjq1rscVJvwEVNVLcrkXu4NR-kZJzgOJq6yAv3eTr~AHEB8BlomE7crPu9RokUiRodPJa1Al4BcTcKSt260O2XHCOwtSjERk3-2deC72VM2LonmALVbi-tf3bLgy7Az34WOqM38nj5Rt4XbzB-uR1WgTKTY~EYw6WFzDjNDquysl92Y7ZdTztQdDig__"
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
            "This Song was Sung by the Chilean Singer \"Christel\" in the popular Chilean Television Show \"Rojo Fama Contrafama\" is now getting really viral on social media these days. Enjoy the full song!"
        const val COURSE_DOCUMENT_URL =
            "https://vk.com/doc26879026_667397256?hash=rzBA9TMriYBMiAeZ897JFlgN16zDCYgMjzjvEjCNXrT&dl=2sJzIf4MVRnSPefLu4nOf7b7Ufla1oCvEdEypGPuM2s"
        const val COURSE_DOCUMENT_TITLE = "Программирование на Kotlin для Android"
    }
}
