package com.shivam.githubify.component

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.shivam.githubify.R
import com.shivam.githubify.data.model.RepoData
import com.shivam.githubify.data.model.langColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RepoCard(
    repo: RepoData,
    isExpanded: Boolean,
    onCardClick: () -> Unit
) {
    val context = LocalContext.current
    val primaryLanguageColor = repo.languages.firstOrNull()?.let { langColor[it] } ?: Color.Gray
    var isStarred by remember { mutableStateOf(false) }



    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    val updatedDate = try {
        val dateTime = LocalDateTime.parse(repo.updated_at, formatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        "Unknown date"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onCardClick() }
            .border(1.dp, primaryLanguageColor, shape = RoundedCornerShape(8.dp))
            .shadow(
                elevation = 10.dp,
                spotColor = primaryLanguageColor,
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            ConstraintLayout (modifier = Modifier.fillMaxWidth()) {
                val (repoInfo, starIcon) = createRefs()
                val guideline = createGuidelineFromStart(0.9f)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.constrainAs(repoInfo) {
                        start.linkTo(parent.start)
                        end.linkTo(guideline)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    Text(
                        text = repo.name,
                        fontFamily = FontFamily(Font(R.font.zain_bold)),
                        fontSize = 20.sp,
                        maxLines = if (isExpanded) 2 else 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    repo.languages.take(10).forEach { language ->
                        val color = langColor[language] ?: Color.Gray
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = color, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.starred),
                    contentDescription = "Star",
                    tint = if (isStarred) Color.Yellow else Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            isStarred = !isStarred
                        }
                        .constrainAs(starIcon) {
                            start.linkTo(guideline)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
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
                    // Display formatted date
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Updated on: $updatedDate",
                        fontFamily = FontFamily(Font(R.font.zain_regular)),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.fork),
                                    contentDescription = "fork",
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = repo.forks_count.toString(),
                                    fontFamily = FontFamily(Font(R.font.zain_regular)),
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(25.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = "star",
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(Color.Yellow)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = repo.stargazers_count.toString(),
                                    fontFamily = FontFamily(Font(R.font.zain_regular)),
                                    fontSize = 16.sp
                                )
                            }
                        }


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
}
