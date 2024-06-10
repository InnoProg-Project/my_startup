package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.feature.profile.profiledetails.domain.models.Project
import com.innoprog.android.network.data.Response

data class ProjectResponse(val results: List<Project>) : Response()
