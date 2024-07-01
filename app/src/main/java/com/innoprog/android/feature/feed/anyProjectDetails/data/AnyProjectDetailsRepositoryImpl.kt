package com.innoprog.android.feature.feed.anyProjectDetails.data

import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsRepository
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.DocumentModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.ImageModel
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnyProjectDetailsRepositoryImpl @Inject constructor() : AnyProjectDetailsRepository {

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

    override fun getAnyProjectDetails(id: String): Flow<Resource<AnyProjectDetailsModel>> = flow {
        emit(Resource.Success(anyProjectDetails))
    }
}
