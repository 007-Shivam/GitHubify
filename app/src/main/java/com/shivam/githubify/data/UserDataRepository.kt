package com.shivam.githubify.data

import com.shivam.githubify.data.model.FollowingFollowersData
import com.shivam.githubify.data.model.RepoData
import com.shivam.githubify.data.model.UserData
import kotlinx.coroutines.flow.Flow

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

interface UserDataRepository {
    suspend fun getUser(username: String): Flow<Result<UserData>>
    suspend fun getRepos(username: String): Flow<Result<List<RepoData>>>
    suspend fun getFollowers(username: String): Flow<Result<List<FollowingFollowersData>>>
    suspend fun getFollowing(username: String): Flow<Result<List<FollowingFollowersData>>>
}