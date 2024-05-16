package com.innoprog.android.feature.edit.data

import com.innoprog.android.feature.edit.data.dto.EditDto
import com.innoprog.android.network.data.Response

interface EditContentNetworkClient {

    suspend fun createIdea(dto: EditDto): Response

}
