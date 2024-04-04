package com.innoprog.android.feature.edit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innoprog.android.base.BaseViewModel
import javax.inject.Inject

class CreateEditContentViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableLiveData<CreateEditContentState>()

    val state: LiveData<CreateEditContentState> = _state

    fun setEditorType(typeContentArgs: TypeContentArgs) {
        when (typeContentArgs) {
            is TypeContentArgs.CreateIdea -> setState(CreateEditContentState.CreateIdea)

            is TypeContentArgs.CreatePublication -> TODO()
            is TypeContentArgs.EditPublication -> TODO()
        }
    }

    private fun setState(state: CreateEditContentState) {
        _state.value = state
    }

}
