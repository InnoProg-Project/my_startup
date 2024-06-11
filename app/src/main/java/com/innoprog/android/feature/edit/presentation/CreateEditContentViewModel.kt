package com.innoprog.android.feature.edit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.useCase.CreateIdeaUseCase
import com.innoprog.android.feature.edit.domain.useCase.CreatePublishUseCase
import com.innoprog.android.feature.edit.domain.useCase.EditePublishUseCase
import com.innoprog.android.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateEditContentViewModel @Inject constructor(
    private val createIdeaUseCase: CreateIdeaUseCase,
    private val createPublishUseCase: CreatePublishUseCase,
    private val editePublishUseCase: EditePublishUseCase,
) : BaseViewModel() {

    private val _state = MutableLiveData<CreateEditContentState>()

    val state: LiveData<CreateEditContentState> = _state


    fun setEditorType(typeContentArgs: TypeContentArgs) {
        when (typeContentArgs) {
            is TypeContentArgs.CreateIdea -> setState(CreateEditContentState.CreateIdea)

            else -> setState(CreateEditContentState.CreateIdea)

//            is TypeContentArgs.CreatePublication -> TODO()
//
//            is TypeContentArgs.EditPublication -> TODO()
        }
    }

    private fun setState(state: CreateEditContentState) {
        _state.value = state
    }

    fun addMediaToLoadList(mediaPath: String) {
        viewModelScope.launch {
            when (val resource = createIdeaUseCase.addMediaToListAttachments(mediaPath)) {
                is Resource.Error -> setState(CreateEditContentState.Error(resource.errorType.name))
                is Resource.Success -> setState(CreateEditContentState.MediaAttachList(resource.data))
            }
        }
    }

    fun deleteMediaFromListAttachments(path: String) {
        viewModelScope.launch {
            when (val resource = createIdeaUseCase.deleteMediaFromListAttachments(path)) {
                is Resource.Error -> setState(CreateEditContentState.Error(resource.errorType.name))
                is Resource.Success -> setState(CreateEditContentState.MediaAttachList(resource.data))
            }
        }
    }

    fun getMediaAttachments() {
        viewModelScope.launch {
            when (val resource = createIdeaUseCase.getMediaAttachments()) {
                is Resource.Success -> setState(CreateEditContentState.MediaAttachList(resource.data))
                else -> setState(CreateEditContentState.MediaAttachList(null))
            }
        }

    }

    fun saveIdea(title: String, content: String) {
        viewModelScope.launch {
            createIdeaUseCase.createIdea(
                IdeaModel(title, content)
            )
        }
    }

}
