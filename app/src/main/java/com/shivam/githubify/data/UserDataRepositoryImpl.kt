package com.shivam.githubify.data

import android.util.Log
import coil.network.HttpException
import com.shivam.githubify.data.model.FollowingFollowersData
import com.shivam.githubify.data.model.RepoData
import com.shivam.githubify.data.model.UserData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

class UserDataRepositoryImpl(
    private val api: Api
) : UserDataRepository {
    override suspend fun getUser(username: String): Flow<Result<UserData>> {
        return flow {
            try {
                Log.d("UserDataRepository", "Fetching user data for $username")
                val userFromApi = api.getUser(username)
                Log.d("UserDataRepository", "User data fetched successfully")
                emit(Result.Success(userFromApi))
            } catch (e: IOException) {
                Log.e("UserDataRepository", "IOException: ${e.message}")
                emit(Result.Error(message = "Error loading user data"))
            } catch (e: HttpException) {
                Log.e("UserDataRepository", "HttpException: ${e.message}")
                emit(Result.Error(message = "Error loading user data"))
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Exception: ${e.message}")
                emit(Result.Error(message = "Error loading user data"))
            }
        }
    }

    override suspend fun getRepos(username: String): Flow<Result<List<RepoData>>> {
        return flow {
            try {
                val allRepos = mutableListOf<RepoData>()
                val perPage = 30
                var page = 1
                while (true) {
                    val repos = api.getRepos(username, page, perPage)
                    if (repos.isEmpty()) break
                    allRepos.addAll(repos)
                    page++
                }

                // Fetch languages for each repo concurrently
                coroutineScope {
                    allRepos.map { repo ->
                        async {
                            val languages = api.getLanguages(repo.languages_url)
                            repo.languages = languages.keys.toList()
                        }
                    }.awaitAll()
                }

                emit(Result.Success(allRepos))
            } catch (e: IOException) {
                Log.e("UserDataRepository", "IOException: ${e.message}")
                emit(Result.Error(message = "Error loading repos"))
            } catch (e: HttpException) {
                Log.e("UserDataRepository", "HttpException: ${e.message}")
                emit(Result.Error(message = "Error loading repos"))
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Exception: ${e.message}")
                emit(Result.Error(message = "Error loading repos"))
            }
        }
    }

    override suspend fun getFollowers(username: String): Flow<Result<List<FollowingFollowersData>>> {
        return flow {
            try {
                val followers = api.getFollowers(username)
                emit(Result.Success(followers))
            } catch (e: IOException) {
                emit(Result.Error(message = "Error loading followers"))
            } catch (e: HttpException) {
                emit(Result.Error(message = "Error loading followers"))
            } catch (e: Exception) {
                emit(Result.Error(message = "Error loading followers"))
            }
        }
    }

    override suspend fun getFollowing(username: String): Flow<Result<List<FollowingFollowersData>>> {
        return flow {
            try {
                val following = api.getFollowing(username)
                emit(Result.Success(following))
            } catch (e: IOException) {
                emit(Result.Error(message = "Error loading following"))
            } catch (e: HttpException) {
                emit(Result.Error(message = "Error loading following"))
            } catch (e: Exception) {
                emit(Result.Error(message = "Error loading following"))
            }
        }
    }
}
