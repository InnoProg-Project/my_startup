package com.innoprog.android.feature.edit.presentation

import com.innoprog.android.feature.edit.domain.model.ProjectModel
import com.innoprog.android.feature.edit.domain.model.PublicationModel

sealed interface CreateEditContentState {
    data object CreateIdea : CreateEditContentState

    data class CreatePublication(val project: ProjectModel) : CreateEditContentState

    data class EditPublication(val project: ProjectModel, val publication: PublicationModel) :
        CreateEditContentState
}
