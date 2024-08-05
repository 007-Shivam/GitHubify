package com.shivam.githubify.activity

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shivam.githubify.R
import com.shivam.githubify.data.model.RepoData
import com.shivam.githubify.data.model.UserData
import com.shivam.githubify.presentation.UserDataViewModel
import com.shivam.githubify.data.model.langColor

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetails(
    user: UserData?,
    repos: List<RepoData>,
    navController: NavHostController,
    viewModel: UserDataViewModel
) {
    val (expandedCardIndex, setExpandedCardIndex) = remember { mutableStateOf(0) }

    if (user != null) {
        viewModel.fetchRepos(user.login)

        BackHandler {
            viewModel.clearUserData()
            navController.navigateUp()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Fixed header for Image and Name
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (!user.name.isNullOrEmpty()) {
                    Text(
                        text = user.name,
                        fontFamily = FontFamily(Font(R.font.zain_bold)),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp
                    )
                }
            }

            // Scrollable content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                item {
                    // Scrollable content for location and bio
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!user.location.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(5.dp))

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
                    }
                }

                // Sticky header for Repo Numbers, Following, and Followers
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
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

                        // Sticky header for "Repositories" title
                        Text(
                            text = "Repositories",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            fontFamily = FontFamily(Font(R.font.zain_bold)),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Scrollable list of repositories
                itemsIndexed(repos) { index, repo ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp, 0.dp)
                    ) {
                        RepoCard(
                            repo = repo,
                            isExpanded = index == expandedCardIndex,
                            onCardClick = {
                                setExpandedCardIndex(if (index == expandedCardIndex) -1 else index)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RepoCard(repo: RepoData,
             isExpanded: Boolean,
             onCardClick: () -> Unit
) {
    val languageColor = langColor[repo.language]
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = repo.name,
                    fontFamily = FontFamily(Font(R.font.zain_bold)),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                repo.language?.let {
                    languageColor?.let { color ->
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = color, shape = CircleShape)
                        )
                    }
                }

            }

            repo.description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    fontFamily = FontFamily(Font(R.font.zain_regular)),
                    fontSize = 12.sp,
                    lineHeight = 10.sp
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Spacer(modifier = Modifier.height(4.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth(0.5f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.fork),
                                contentDescription = "fork",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = repo.forks_count.toString(),
                                fontFamily = FontFamily(Font(R.font.zain_regular)),
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = "star",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = repo.stargazers_count.toString(),
                                fontFamily = FontFamily(Font(R.font.zain_regular)),
                                fontSize = 16.sp
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(4.dp))



                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            // Open the URL in the browser
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.html_url))
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Go to Repo")
                    }
                }
            }
        }
    }
}
