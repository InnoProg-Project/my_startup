package com.innoprog.android.feature.edit.presentation.model

import java.io.Serializable

sealed class TypeContentArgs : Serializable {
    data object CreateIdea : TypeContentArgs(),Serializable {
        private fun readResolve(): Any = CreateIdea
    }

    data class CreatePublication(val projectId: Int) : TypeContentArgs(),Serializable

    data class CreateEditPublication(val projectId: Int) : TypeContentArgs(),Serializable
}
