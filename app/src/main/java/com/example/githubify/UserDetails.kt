package com.example.githubify

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.githubify.data.model.UserData

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@Composable
fun UserDetails(user: UserData?) {
    if (user != null) {
        Text(text = "Name: ${user.name}")
        // Add more details if needed
    } else {
        Text(text = "No user data available")
    }
}
