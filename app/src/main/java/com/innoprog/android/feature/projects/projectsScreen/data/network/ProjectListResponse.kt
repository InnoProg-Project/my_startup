package com.innoprog.android.feature.projects.projectsScreen.data.network

import com.innoprog.android.feature.projects.data.dto.ProjectDto
import com.innoprog.android.network.data.Response

class ProjectListResponse(val result: List<ProjectDto>) : Response()