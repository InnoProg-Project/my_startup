package com.innoprog.android.feature.profile.profiledetails.data.network

sealed interface RequestByProfile {
    data object GetProfile : RequestByProfile
    data object GetProfileCompany : RequestByProfile
    class GetAll(val authorId: String) : RequestByProfile
    class GetProjects(val type: String, val authorId: String) : RequestByProfile
    class GetIdeas(val type: String, val authorId: String) : RequestByProfile
    class GetLikes(val pageSize: Int) : RequestByProfile
    class GetFavorites(val pageSize: Int) : RequestByProfile
    class GetProjectById(val id: String) : RequestByProfile
    class GetProfileById(val id: String) : RequestByProfile
}
