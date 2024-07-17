package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.feature.profile.profiledetails.data.dto.model.FeedDto
import com.innoprog.android.feature.profile.profiledetails.data.dto.model.ProjectDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {

    @GET("/v1/profile")
    suspend fun loadProfile(): Response<ProfileResponse>

    @GET("/v1/profile/{profileId}")
    suspend fun loadProfileById(@Path("profileId") id: String): Response<ProfileResponse>

    @GET("/v1/profile/company")
    suspend fun loadProfileCompany(): Response<ProfileCompanyResponse>

    @GET("/v1/feed")
    suspend fun getAll(
        @Query("authorId") authorId: String
    ): Response<List<FeedDto>>

    @GET("/v1/feed")
    suspend fun getProjects(
        @Query("type") type: String,
        @Query("authorId") authorId: String
    ): Response<List<FeedDto>>

    @GET("/v1/feed")
    suspend fun getIdeas(
        @Query("type") type: String,
        @Query("authorId") authorId: String
    ): Response<List<FeedDto>>

    @GET("/v1/feed/likes")
    suspend fun getLikes(
        @Query("pageSize") pageSize: Int
    ): Response<List<FeedDto>>

    @GET("/v1/feed/favorites")
    suspend fun getFavorites(
        @Query("pageSize") pageSize: Int
    ): Response<List<FeedDto>>

    @GET("/v1/projects/{projectId}")
    suspend fun getProjectById(@Path("projectId") id: String): Response<ProjectDto>
}
