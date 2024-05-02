package com.innoprog.android.feature.projects.projectsScreen.data

import com.innoprog.android.feature.projects.projectsScreen.domain.ProjectsScreenRepository
import com.innoprog.android.feature.projects.projectsScreen.domain.model.ProjectScreenModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProjectsScreenRepositoryImpl @Inject constructor() : ProjectsScreenRepository {

    val models = listOf(
        ProjectScreenModel(ID, NAME_1, SHORT_DESCRIPTION, DESCRIPTION_1, LOGO_URL),
        ProjectScreenModel(ID, NAME_2, SHORT_DESCRIPTION, DESCRIPTION_2, LOGO_URL)
    )

    override fun getProjects(): Flow<Pair<List<ProjectScreenModel>?, ErrorStatus?>> = flow {
        emit(Pair(models, null))
    }

    companion object {

        const val ID = "-1"
        const val LOGO_URL = "https://studsovet21.ru/upload/iblock/cdc/4yc9xgl01u6ynds0txpe2r1g1xnn1k20.jpg"
        const val SHORT_DESCRIPTION = "Искуственный интеллект"
        const val NAME_1 = "Мой Стартап"
        const val DESCRIPTION_1 = "Проект для помощи генерации идей и изучения проектов"
        const val NAME_2 = "Famuly app"
        const val DESCRIPTION_2 = "Надежный помощник предназначен для облегчения путей родительства"
    }
}
