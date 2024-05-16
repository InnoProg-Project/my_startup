package com.innoprog.android.feature.edit.data.dto

import okhttp3.MultipartBody


data class EditDto(
    val data: EditableDataDto,
    val attachments: List<MultipartBody.Part>
)
sealed interface EditableDataDto {

    data class IdeaData(
        val type: String = IDEA,
        val title: String,
        val content: String,
        val projectId: String,
    ) : EditableDataDto

    data class PublishData(
        val type: String = NEWS,
        val title: String,
        val content: String,
        val projectId: String
    ) : EditableDataDto

    data class EditablePublishData(
        val title: String,
        val content: String,
        val deletingAttachments: List<String> //список удаляемого медиа
    ) : EditableDataDto

    companion object {
        const val NEWS = "NEWS"
        const val IDEA = "IDEA"
    }
}

