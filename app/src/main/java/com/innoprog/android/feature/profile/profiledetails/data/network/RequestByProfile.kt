package com.innoprog.android.feature.profile.profiledetails.data.network

sealed interface RequestByProfile {
    data object GetProfile : RequestByProfile
    data object GetProfileCompany : RequestByProfile
    data class GetAll(val authorId: String) : RequestByProfile
    data class GetProjects(val type: String, val authorId: String) : RequestByProfile
    data class GetIdeas(val type: String, val authorId: String) : RequestByProfile
    data class GetLikes(val pageSize: Int) : RequestByProfile
    data class GetFavorites(val pageSize: Int) : RequestByProfile
}
