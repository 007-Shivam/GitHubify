package com.example.githubify.data

import com.example.githubify.data.model.UserData
import kotlinx.coroutines.flow.Flow

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

interface UserDataRepository {
    suspend fun getUser(username: String): Flow<Result<UserData>>
}