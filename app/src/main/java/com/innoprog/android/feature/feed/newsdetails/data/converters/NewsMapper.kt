package com.innoprog.android.feature.feed.newsdetails.data.converters

import com.innoprog.android.feature.feed.newsdetails.data.network.AttachmentDto
import com.innoprog.android.feature.feed.newsdetails.data.network.AuthorDto
import com.innoprog.android.feature.feed.newsdetails.data.network.CompanyDto
import com.innoprog.android.feature.feed.newsdetails.data.network.NewsDto
import com.innoprog.android.feature.feed.newsdetails.domain.models.Author
import com.innoprog.android.feature.feed.newsdetails.domain.models.Company
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.feed.newsfeed.domain.models.AttachmentType

fun NewsDto.mapToNewsDetails(): NewsDetailsModel {
    return NewsDetailsModel(
        id = id,
        type = type,
        author = createAuthor(author),
        projectId = projectId,
        coverUrl = createUrls(attachments),
        title = title,
        content = content,
        publishedAt = publishedAt,
        likesCount = likesCount,
        commentsCount = commentsCount,
        isLiked = isLiked,
        isFavorite = isFavorited,
        project = null
    )
}

private fun createUrls(urlList: List<AttachmentDto>): List<String> {
    return urlList.filter { it.type == AttachmentType.IMAGE.value }.map { it.filePath }
}

private fun createAuthor(authorDto: AuthorDto): Author {
    return authorDto.let {
        Author(
            id = it.id,
            name = it.name,
            company = createCompany(it.company)
        )
    }
}

private fun createCompany(companyDto: CompanyDto): Company {
    return companyDto.let {
        Company(
            companyName = it.companyName,
            role = it.role,
        )
    }
}
