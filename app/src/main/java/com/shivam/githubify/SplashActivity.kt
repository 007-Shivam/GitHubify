package com.shivam.githubify

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.identity.Identity
import com.shivam.githubify.activity.sign_in.GoogleAuthUiClient
import com.shivam.githubify.ui.theme.GitHubifyTheme
import kotlinx.coroutines.delay

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubifyTheme {
                SplashScreen()
            }
        }
    }

    @Preview
    @Composable
    private fun SplashScreen(){
        val context = LocalContext.current
        val alpha = remember{
            androidx.compose.animation.core.Animatable(0f)
        }

        LaunchedEffect(key1 = true) {
            alpha.animateTo(1f,
                animationSpec = tween(1500)
            )
            delay(1500)
            // Check sign-in status and navigate accordingly
            val googleAuthUiClient = GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context)
            )
            val isSignedIn = googleAuthUiClient.getSignedInUser() != null

            // Start MainActivity with the appropriate flags and finish SplashActivity
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("IS_SIGNED_IN", isSignedIn)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
            (context as Activity).finish()
        }

        Box (modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.alpha(alpha.value),
                painter = painterResource(id = R.drawable.github),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}
