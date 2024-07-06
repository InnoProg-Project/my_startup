package com.innoprog.android.feature.feed.newsfeed.data.converters

import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectDto
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project

fun ProjectDto.mapToProjectDomain(): Project {
    return Project(
        id = id,
        name = name,
        area = area,
        logoUrl = logoFilePath,
    )
}
