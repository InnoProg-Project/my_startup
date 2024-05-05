package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.domain.model.Body
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface EditBodyProfileUseCase {
    suspend fun editProfile(): Flow<Resource<Body>>
}
