package com.innoprog.android.feature.projects.projectsScreen.data.network

import com.innoprog.android.network.data.Response

interface ProjectListNetworkClient {
    suspend fun getProjectList(): Response
}