package com.shivam.githubify.activity.sign_in

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)