package com.innoprog.android.feature.profile.profiledetails.domain.models

import com.innoprog.android.feature.feed.newsfeed.domain.models.Project

class FeedWithProject(
    val feedWrapper: FeedWrapper,
    val project: Project?
)