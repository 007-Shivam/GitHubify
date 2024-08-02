package com.shivam.githubify.activity

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shivam.githubify.R
import com.shivam.githubify.data.model.RepoData
import com.shivam.githubify.data.model.UserData
import com.shivam.githubify.presentation.UserDataViewModel

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@Composable
fun UserDetails(
    user: UserData?,
    repos: List<RepoData>,
    navController: NavHostController,
    viewModel: UserDataViewModel)
{

    if (user != null) {
        viewModel.fetchRepos(user.login)

        BackHandler {
            viewModel.clearUserData()
            navController.navigateUp()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = {
                viewModel.clearUserData()
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
                Text(text = "Back", fontSize = 16.sp)
            }
        }

        Column (
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = user.avatar_url,
                contentDescription = null,
                modifier = Modifier
                    .border(
                        BorderStroke(4.dp, MaterialTheme.colorScheme.inversePrimary),
                        CircleShape
                    )
                    .shadow(
                        elevation = 20.dp,
                        spotColor = Color.LightGray,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (!user.name.isNullOrEmpty()) {
                Text(
                    text = "${user.name}",
                    fontFamily = FontFamily(Font(R.font.zain_bold)),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }

            if (!user.location.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Rounded.LocationOn,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = user.location,
                        fontFamily = FontFamily(Font(R.font.zain_bold)),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                    )
                }
            }


            if (!user.bio.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = user.bio,
                    fontFamily = FontFamily(Font(R.font.zain_regular)),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            }



            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Repos: ",
                        fontFamily = FontFamily(Font(R.font.zain_regular)),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )

                    Text(
                        text = user.public_repos.toString(),
                        fontFamily = FontFamily(Font(R.font.zain_bold)),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Following: ",
                        fontFamily = FontFamily(Font(R.font.zain_regular)),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )

                    Text(
                        text = user.following.toString(),
                        fontFamily = FontFamily(Font(R.font.zain_bold)),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Followers: ",
                        fontFamily = FontFamily(Font(R.font.zain_regular)),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )

                    Text(
                        text = user.followers.toString(),
                        fontFamily = FontFamily(Font(R.font.zain_bold)),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }

            Text(
                text = "Repositories",
                fontFamily = FontFamily(Font(R.font.zain_bold)),
                fontSize = 24.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                LazyColumn {
                    items(repos) { repo ->
                        RepoCard(repo = repo)
                    }
                }
            }
        }
    }
}

@Composable
fun RepoCard(repo: RepoData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = repo.name,
                fontFamily = FontFamily(Font(R.font.zain_bold)),
                fontSize = 20.sp
            )

            repo.description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    fontFamily = FontFamily(Font(R.font.zain_regular)),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Language: ${repo.language ?: "N/A"}",
                fontFamily = FontFamily(Font(R.font.zain_regular)),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "URL: ${repo.html_url}",
                fontFamily = FontFamily(Font(R.font.zain_regular)),
                fontSize = 16.sp
            )
        }
    }
}