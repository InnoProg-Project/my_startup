package com.innoprog.android.feature.feed.newsdetails.data.converters

import com.innoprog.android.feature.feed.newsdetails.data.network.CommentDto
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentAuthor
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel

fun CommentDto.mapToCommentModel(): CommentModel {
    return CommentModel(
        commentId = commentId,
        publicationId = publicationId,
        commentAuthor = CommentAuthor(
            id = commentAuthor.id,
            name = commentAuthor.name
        ),
        commentContent = commentContent,
        commentCreationDate = commentCreationDate
    )
}
