package com.shivam.githubify.data

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(data: T? = null, message: String) : Result<T>(data, message)
}