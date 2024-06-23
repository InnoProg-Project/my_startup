package com.innoprog.android.feature.feed.newsfeed.data.converters

import com.innoprog.android.feature.feed.newsfeed.data.network.Attachments
import com.innoprog.android.feature.feed.newsfeed.data.network.AuthorDto
import com.innoprog.android.feature.feed.newsfeed.data.network.CompanyDto
import com.innoprog.android.feature.feed.newsfeed.data.network.NewsDto
import com.innoprog.android.feature.feed.newsfeed.domain.models.Author
import com.innoprog.android.feature.feed.newsfeed.domain.models.Company
import com.innoprog.android.feature.feed.newsfeed.domain.models.News

class NewsDtoMapper {
    fun newsDtoToNews(newsDto: NewsDto): News {
        return with(newsDto) {
            News(
                id = id,
                type = type,
                author = createAuthor(author),
                projectId = projectId,
                coverUrl = createUrls(attachments),
                title = title,
                content = content,
                publishedAt = publishedAt,
                likesCount = likesCount,
                commentsCount = commentsCount
            )
        }
    }

    private fun createUrls(urlList: List<Attachments>): List<String> {
        return urlList.map { it.filePath }
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
}
