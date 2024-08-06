package com.shivam.githubify.compoenet

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    // Format the updated_at date
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
                repo.languages.forEach { language ->
                    val color = langColor[language] ?: Color.Gray
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(color = color, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
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
