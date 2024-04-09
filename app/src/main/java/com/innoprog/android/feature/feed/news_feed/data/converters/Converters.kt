package com.innoprog.android.feature.feed.news_feed.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.innoprog.android.feature.feed.news_feed.data.db.AuthorEntity
import com.innoprog.android.feature.feed.news_feed.data.db.CompanyEntity

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAuthorEntity(author: AuthorEntity): String {
        return gson.toJson(author)
    }

    @TypeConverter
    fun toAuthorEntity(authorString: String): AuthorEntity {
        return gson.fromJson(authorString, AuthorEntity::class.java)
    }

    @TypeConverter
    fun fromCompanyEntity(company: CompanyEntity): String {
        return gson.toJson(company)
    }

    @TypeConverter
    fun toCompanyEntity(companyString: String): CompanyEntity {
        return gson.fromJson(companyString, CompanyEntity::class.java)
    }
}
