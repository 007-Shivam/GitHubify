package com.shivam.githubify.data

import com.shivam.githubify.data.model.UserData
import kotlinx.coroutines.flow.Flow

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

interface UserDataRepository {
    suspend fun getUser(username: String): Flow<Result<UserData>>
}