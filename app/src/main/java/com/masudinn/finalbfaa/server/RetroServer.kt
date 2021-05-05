package com.masudinn.finalbfaa.server

import com.masudinn.finalbfaa.model.GithubUser
import com.masudinn.finalbfaa.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroServer {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q")
        q: String?
    ): SearchResponse

    @GET("users/{username}")
    suspend fun userDetail(
        @Path("username")
        username: String?
    ): GithubUser

    @GET("users/{username}/followers")
    suspend fun userFollower(
        @Path("username")
        username: String?
    ): List<GithubUser>

    @GET("users/{username}/following")
    suspend fun userFollowing(
        @Path("username")
        username: String?
    ): List<GithubUser>

}