package com.innoprog.android.feature.edit.data.dto


data class EditDto(
    val data: EditableDataDto,
    val attachments: List<String>
)

sealed interface EditableDataDto {

    data class IdeaDto(
        val type: String = IDEA,
        val title: String,
        val content: String,
        val projectId: String,
    ) : EditableDataDto

    data class PublishDto(
        val type: String = NEWS,
        val title: String,
        val content: String,
        val projectId: String
    ) : EditableDataDto

    data class EditablePublishDto(
        val title: String,
        val content: String,
        val deletingAttachments: List<String> //список удаляемого медиа
    ) : EditableDataDto

    companion object {
        const val NEWS = "NEWS"
        const val IDEA = "IDEA"
    }
}

