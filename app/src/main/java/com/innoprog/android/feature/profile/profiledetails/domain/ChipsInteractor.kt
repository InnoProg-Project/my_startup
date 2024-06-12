package com.innoprog.android.feature.profile.profiledetails.domain

import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.profile.profiledetails.domain.models.Project
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChipsInteractor {

    suspend fun getAll(authorId: String): Flow<Resource<List<News>>>
    suspend fun getProjects(userId: String): Flow<Resource<List<Project>>>
    suspend fun getIdeas(type: String, userId: String): Flow<Resource<List<News>>>
    suspend fun getLikes(lastId: String, pageSize: Int): Flow<Resource<List<News>>>
    suspend fun getFavorites(lastId: String, pageSize: Int): Flow<Resource<List<News>>>
}
