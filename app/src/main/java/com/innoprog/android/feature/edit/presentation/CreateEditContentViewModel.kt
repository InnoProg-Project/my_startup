package com.innoprog.android.feature.edit.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.edit.domain.useCase.CreateIdeaUseCase
import com.innoprog.android.feature.edit.domain.useCase.CreatePublishUseCase
import com.innoprog.android.feature.edit.domain.useCase.EditePublishUseCase
import javax.inject.Inject

class CreateEditContentViewModel @Inject constructor(
    private val createIdeaUseCase: CreateIdeaUseCase,
    private val createPublishUseCase: CreatePublishUseCase,
    private val editePublishUseCase: EditePublishUseCase,
) : BaseViewModel() {

    private val _state = MutableLiveData<CreateEditContentState>()

    val state: LiveData<CreateEditContentState> = _state

    val mediaList = mutableListOf<Uri>()

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

    fun addMediaToLoadList(uri: Uri) {
        mediaList.add(uri)
    }

}
