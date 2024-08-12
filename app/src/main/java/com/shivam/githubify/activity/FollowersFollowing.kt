package com.shivam.githubify.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.shivam.githubify.data.model.FollowingFollowersData
import com.shivam.githubify.presentation.UserDataViewModel

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowersFollowingScreen(
    title: String,
    users: List<FollowingFollowersData>,
    navController: NavHostController,
    onBackClick: () -> Unit,
    viewModel: UserDataViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box (
            modifier = Modifier.padding(bottom = 70.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(users) { user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                viewModel.fetchUser(user.login)
                                navController.navigate("UserDetails")
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SubcomposeAsyncImage(
                            model = user.avatar_url,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = user.login, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}
