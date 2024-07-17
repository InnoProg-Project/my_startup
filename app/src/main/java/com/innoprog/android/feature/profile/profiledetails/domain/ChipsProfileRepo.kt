package com.innoprog.android.feature.profile.profiledetails.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWithProject
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChipsProfileRepo {
    suspend fun getAll(authorId: String): Flow<Resource<List<FeedWithProject>>>
    suspend fun getProjects(type: String, userId: String): Flow<Resource<List<FeedWithProject>>>
    suspend fun getIdeas(type: String, userId: String): Flow<Resource<List<FeedWithProject>>>
    suspend fun getLikes(pageSize: Int): Flow<Resource<List<FeedWithProject>>>
    suspend fun getFavorites(pageSize: Int): Flow<Resource<List<FeedWithProject>>>
}
