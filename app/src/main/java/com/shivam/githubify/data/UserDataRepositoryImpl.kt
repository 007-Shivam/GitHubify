package com.shivam.githubify.data

import coil.network.HttpException
import com.shivam.githubify.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

class UserDataRepositoryImpl (
    private val api: Api
): UserDataRepository {
    override suspend fun getUser(username: String): Flow<Result<UserData>> {
        return flow {
            val userFromApi = try {
                api.getUser(username)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user data"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user data"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user data"))
                return@flow
            }
            emit(Result.Success(userFromApi))
        }
    }
}