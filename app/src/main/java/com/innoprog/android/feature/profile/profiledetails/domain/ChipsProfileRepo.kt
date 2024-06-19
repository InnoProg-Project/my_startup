package com.innoprog.android.feature.profile.profiledetails.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChipsProfileRepo {
    suspend fun getAll(authorId: String): Flow<Resource<List<FeedWrapper>>>
    suspend fun getProjects(type: String, userId: String): Flow<Resource<List<FeedWrapper.News>>>
    suspend fun getIdeas(type: String, userId: String): Flow<Resource<List<FeedWrapper.Idea>>>
    suspend fun getLikes(pageSize: Int): Flow<Resource<List<FeedWrapper>>>
    suspend fun getFavorites(pageSize: Int): Flow<Resource<List<FeedWrapper>>>
}
