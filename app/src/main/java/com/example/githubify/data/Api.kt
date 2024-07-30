package com.example.githubify.data

import com.example.githubify.data.model.UserData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

interface Api {

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): UserData

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

}