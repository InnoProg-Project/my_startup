package com.innoprog.android.feature.profile.profiledetails.domain.impl

import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsInteractor
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsProfileRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Project
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

class ChipsInteractorImpl(private val repo: ChipsProfileRepo): ChipsInteractor {

    override suspend fun getAll(authorId: String): Flow<Resource<List<News>>> {
        return repo.getAll(authorId)
    }

    override suspend fun getProjects(userId: String): Flow<Resource<List<Project>>> {
        return repo.getProjects(userId)
    }

    override suspend fun getIdeas(type: String, userId: String): Flow<Resource<List<News>>> {
        return repo.getIdeas(type, userId)
    }

    override suspend fun getLikes(lastId: String, pageSize: Int): Flow<Resource<List<News>>> {
        return repo.getLikes(lastId, pageSize)
    }

    override suspend fun getFavorites(lastId: String, pageSize: Int): Flow<Resource<List<News>>> {
        return repo.getFavorites(lastId, pageSize)
    }
}
