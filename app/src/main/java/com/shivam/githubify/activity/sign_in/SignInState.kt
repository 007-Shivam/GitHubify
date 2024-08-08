package com.shivam.githubify.activity.sign_in

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)