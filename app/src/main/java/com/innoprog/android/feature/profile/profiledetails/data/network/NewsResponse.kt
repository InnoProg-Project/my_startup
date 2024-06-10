package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.network.data.Response

class NewsResponse(val results: List<News>) : Response()
