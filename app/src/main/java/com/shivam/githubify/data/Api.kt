package com.shivam.githubify.data

import com.shivam.githubify.data.model.RepoData
import com.shivam.githubify.data.model.UserData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

interface Api {
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): UserData

    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<RepoData>

    @GET
    suspend fun getLanguages(
        @Url languagesUrl: String
    ): Map<String, Int>


    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}
