package com.innoprog.android.feature.edit.data.dto

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody


data class EditDto(
    val data: EditableDataDto,
    val attachments: List<MultipartBody.Part>
)
sealed interface EditableDataDto {

    data class IdeaData(
        @SerializedName("type")
        val type: String = IDEA,
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("projectId")
        val projectId: String,
    ) : EditableDataDto

    data class PublishData(
        @SerializedName("type")
        val type: String = NEWS,
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("projectId")
        val projectId: String
    ) : EditableDataDto

    data class EditablePublishData(
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("deletingAttachments")
        val deletingAttachments: List<String> //список удаляемого медиа
    ) : EditableDataDto

    companion object {
        const val NEWS = "NEWS"
        const val IDEA = "IDEA"
    }
}

