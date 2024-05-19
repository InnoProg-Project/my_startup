package com.innoprog.android.feature.profile.editingprofile.domain.models

class Profile(
    val userId: String,
    val name: String,
    val about: String,
    val communicationChannels: List<CommunicationChannel>,
    val authorities: List<String>
)
