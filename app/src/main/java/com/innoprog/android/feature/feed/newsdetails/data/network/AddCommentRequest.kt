package com.innoprog.android.feature.feed.newsdetails.data.network

import com.google.gson.annotations.SerializedName

data class AddCommentRequest(@SerializedName("content") val content: String)
