package com.innoprog.android.feature.profile.profiledetails.data.network

sealed interface Request {
    data object GetProfile : Request
    data object GetProfileCompany : Request
    data class GetAll(val authorId: String) : Request
    data class GetProjects(val authorId: String) : Request
    data class GetIdeas(val type: String, val authorId: String) : Request
    data class GetLikes(val lastId: String, val pageSize: Int) : Request
    data class GetFavorites(val lastId: String, val pageSize: Int) : Request
}
