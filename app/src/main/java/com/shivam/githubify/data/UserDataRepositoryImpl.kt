package com.shivam.githubify.data

import android.util.Log
import coil.network.HttpException
import com.shivam.githubify.data.model.RepoData
import com.shivam.githubify.data.model.UserData
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
                var page = 1
                val perPage = 30
                while (true) {
                    Log.d("UserDataRepository", "Fetching repos for $username, page $page")
                    val repos = api.getRepos(username, page, perPage)
                    if (repos.isEmpty()) {
                        break
                    }
                    allRepos.addAll(repos)
                    page++
                }
                Log.d("UserDataRepository", "Repos fetched successfully")
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
}
