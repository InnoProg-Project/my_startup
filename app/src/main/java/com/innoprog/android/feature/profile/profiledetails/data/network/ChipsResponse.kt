package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.network.data.Response

class ChipsResponse(val results: List<FeedWrapper>) : Response()
