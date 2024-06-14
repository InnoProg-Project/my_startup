package com.innoprog.android.feature.edit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.model.PublicationModel
import com.innoprog.android.feature.edit.domain.useCase.CreateIdeaUseCase
import com.innoprog.android.feature.edit.domain.useCase.CreatePublishUseCase
import com.innoprog.android.feature.edit.domain.useCase.EditePublishUseCase
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateEditContentViewModel @Inject constructor(
    private val createIdeaUseCase: CreateIdeaUseCase,
    private val createPublishUseCase: CreatePublishUseCase,
    private val editePublishUseCase: EditePublishUseCase,
) : BaseViewModel() {

    private val _state = MutableLiveData<CreateEditContentState>()

    val state: LiveData<CreateEditContentState> = _state

    private var projectId: String? = null

    private var publicationId: String? = null

    fun setEditorType(typeContentArgs: TypeContentArgs) {
        when (typeContentArgs) {
            is TypeContentArgs.CreateIdea -> setCreateIdeaState()

            is TypeContentArgs.CreatePublication -> {
                setCreatePublicationState(typeContentArgs.projectId)
            }

            is TypeContentArgs.EditPublication -> {
                setModifyPublicationState(typeContentArgs.projectId, typeContentArgs.publicationId)
            }
        }
    }

    private fun setCreateIdeaState() {
        _state.value = CreateEditContentState.CreateIdea
    }

    private fun setCreatePublicationState(projectId: String) {
        this.projectId = projectId
        _state.value = CreateEditContentState.CreatePublication
        getProjectInfo(projectId)
    }

    private fun setModifyPublicationState(projectId: String, publicationId: String) {
        this.projectId = projectId
        this.publicationId = publicationId
        viewModelScope.launch {

        }
    }

    fun addMediaToLoadList(mediaPath: String) {
        viewModelScope.launch {
            when (val resource = createIdeaUseCase.addMediaToListAttachments(mediaPath)) {
                is Resource.Error -> _state.value =
                    CreateEditContentState.Error(resource.errorType.name)

                is Resource.Success -> _state.value =
                    CreateEditContentState.MediaAttachList(resource.data)
            }
        }
    }

    fun deleteMediaFromListAttachments(path: String) {
        viewModelScope.launch {
            when (val resource = createIdeaUseCase.deleteMediaFromListAttachments(path)) {
                is Resource.Error -> _state.value =
                    CreateEditContentState.Error(resource.errorType.name)

                is Resource.Success -> _state.value =
                    CreateEditContentState.MediaAttachList(resource.data)
            }
        }
    }

    fun getMediaAttachments() {
        viewModelScope.launch {
            when (val resource = createIdeaUseCase.getMediaAttachments()) {
                is Resource.Success -> _state.value =
                    CreateEditContentState.MediaAttachList(resource.data)

                else -> _state.value = CreateEditContentState.MediaAttachList(null)
            }
        }

    }

    fun saveNewIdea(title: String, content: String, onResult: () -> Unit) {
        viewModelScope.launch {
            createIdeaUseCase.createIdea(
                IdeaModel(title, content)
            )
            onResult()
        }
    }

    fun saveNewPublication(title: String, content: String, onResult: () -> Unit) {
        projectId.let {
            viewModelScope.launch {
                createPublishUseCase.createPublication(
                    PublicationModel(
                        projectId = projectId!!,
                        title = title,
                        content = content
                    )
                )
                onResult()
            }
        }
    }

    fun saveModifiedPublication(title: String, content: String, onResult: () -> Unit) {
        if (publicationId != null && projectId != null) {
            viewModelScope.launch {
                editePublishUseCase.updatePublication(
                    PublicationModel(
                        projectId = projectId!!,
                        publicationId = publicationId,
                        title = title,
                        content = content
                    )
                )
                onResult()
            }
        }
    }

    private fun getProjectInfo(projectId: String) {
        viewModelScope.launch {
            _state.value = CreateEditContentState.ProjectInfo(
                createPublishUseCase.getProjectById(projectId).first()
            )
        }
    }
}
