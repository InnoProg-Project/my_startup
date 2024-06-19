package com.innoprog.android.feature.profile.profiledetails.domain.impl

import com.innoprog.android.feature.profile.profiledetails.domain.ChipsInteractor
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsProfileRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChipsInteractorImpl @Inject constructor(private val repo: ChipsProfileRepo) : ChipsInteractor {

    override suspend fun getAll(authorId: String): Flow<Resource<List<FeedWrapper>>> {
        return repo.getAll(authorId)
    }

    override suspend fun getProjects(type: String, userId: String): Flow<Resource<List<FeedWrapper.News>>> {
        return repo.getProjects(type, userId)
    }

    override suspend fun getIdeas(type: String, userId: String): Flow<Resource<List<FeedWrapper.Idea>>> {
        return repo.getIdeas(type, userId)
    }

    override suspend fun getLikes(pageSize: Int): Flow<Resource<List<FeedWrapper>>> {
        return repo.getLikes(pageSize)
    }

    override suspend fun getFavorites(pageSize: Int): Flow<Resource<List<FeedWrapper>>> {
        return repo.getFavorites(pageSize)
    }
}
