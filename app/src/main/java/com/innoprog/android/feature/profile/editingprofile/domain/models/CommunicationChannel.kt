package com.innoprog.android.feature.profile.editingprofile.domain.models

data class CommunicationChannel(
    val id: String,
    val type: String,
    val value: String,
    val verified: Boolean,
    val main: Boolean
)