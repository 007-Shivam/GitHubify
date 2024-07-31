package com.example.githubify.activity

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.githubify.data.model.UserData
import com.example.githubify.presentation.UserDataViewModel

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@Composable
fun UserDetails(user: UserData?, navController: NavHostController, viewModel: UserDataViewModel) {
    if (user != null) {
        BackHandler {
            viewModel.clearUserData()
            navController.navigateUp()
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = {
                viewModel.clearUserData()  // Clear the user data
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
                Text(text = "Back", fontSize = 16.sp)
            }
        }

        Column () {
            AsyncImage(
                model = user.avatar_url,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(text = "Name: ${user.name}")
        }
    }
}
