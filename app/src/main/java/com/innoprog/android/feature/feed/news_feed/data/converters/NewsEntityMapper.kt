package com.innoprog.android.feature.feed.news_feed.data.converters

import com.innoprog.android.feature.feed.news_feed.data.db.AuthorEntity
import com.innoprog.android.feature.feed.news_feed.data.db.CompanyEntity
import com.innoprog.android.feature.feed.news_feed.data.db.NewsEntity
import com.innoprog.android.feature.feed.news_feed.domain.models.Author
import com.innoprog.android.feature.feed.news_feed.domain.models.Company
import com.innoprog.android.feature.feed.news_feed.domain.models.News
import javax.inject.Inject

class NewsEntityMapper @Inject constructor() {
    fun mapNewsEntityToNews(newsEntity: NewsEntity): News {
        return News(
            id = newsEntity.id,
            type = newsEntity.type,
            author = createAuthor(newsEntity.author),
            projectId = newsEntity.projectId,
            coverUrl = newsEntity.coverUrl,
            title = newsEntity.title,
            content = newsEntity.content,
            publishedAt = newsEntity.publishedAt,
            likesCount = newsEntity.likesCount,
            commentsCount = newsEntity.commentsCount
        )
    }

    private fun createAuthor(authorEntity: AuthorEntity): Author {
        return Author(
            id = authorEntity.id,
            avatarUrl = authorEntity.avatarUrl,
            name = authorEntity.name,
            company = createCompany(authorEntity.company)
        )
    }

    private fun createCompany(companyEntity: CompanyEntity): Company {
        return Company(
            companyName = companyEntity.companyName,
            role = companyEntity.role
        )
    }

    fun mapNewsToNewsEntity(news: News): NewsEntity {
        return NewsEntity(
            id = news.id,
            type = news.type,
            author = createAuthorEntity(news.author),
            projectId = news.projectId,
            coverUrl = news.coverUrl,
            title = news.title,
            content = news.content,
            publishedAt = news.publishedAt,
            likesCount = news.likesCount,
            commentsCount = news.commentsCount
        )
    }

    private fun createAuthorEntity(author: Author): AuthorEntity {
        return AuthorEntity(
            id = author.id,
            avatarUrl = author.avatarUrl,
            name = author.name,
            company = createCompanyEntity(author.company)
        )
    }

    private fun createCompanyEntity(company: Company): CompanyEntity {
        return CompanyEntity(
            companyName = company.companyName,
            role = company.role
        )
    }
}
