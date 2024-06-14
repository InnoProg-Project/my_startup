package com.innoprog.android.feature.edit.presentation

import java.io.Serializable

sealed class TypeContentArgs : Serializable {
    data object CreateIdea : TypeContentArgs(), Serializable {
        private fun readResolve(): Any = CreateIdea
    }

    data class CreatePublication(val projectId: String) : TypeContentArgs(), Serializable

    data class EditPublication(val projectId: String, val publicationId: String) :
        TypeContentArgs(), Serializable
}
