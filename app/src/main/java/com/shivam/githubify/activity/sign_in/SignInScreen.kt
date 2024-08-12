package com.shivam.githubify.activity.sign_in

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shivam.githubify.R

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@Composable
fun SignInScreen(
    navController: NavHostController,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    val logoSize = remember { Animatable(360f) }

    LaunchedEffect(Unit) {
        logoSize.animateTo(
            targetValue = 200f,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            )
        )
    }

    // Text fade-in animation
    val textAlpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                delayMillis = 300
            )
        )
    }

    // Button slide-in animation
    val buttonOffsetY = remember { Animatable(300f) }
    LaunchedEffect(Unit) {
        buttonOffsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 500,
                easing = FastOutSlowInEasing
            )
        )
    }

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.github),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(logoSize.value.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.zain_extrabold)),
            fontSize = 40.sp,
            modifier = Modifier
                .padding(8.dp)
                .alpha(textAlpha.value)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.login_des),
            fontFamily = FontFamily(Font(R.font.zain_regular)),
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(textAlpha.value)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
//                .border(
//                    BorderStroke(width = 1.dp, color = Color.Black),
//                    shape = RoundedCornerShape(50.dp)
//                )
                .width(230.dp)
                .height(50.dp)
                .offset(y = buttonOffsetY.value.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(95, 149, 243)),
            content = {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "login_btn",
                    modifier = Modifier
                        .size(25.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = stringResource(id = R.string.login_btn),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                )
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextButton(
            onClick = {
            navController.navigate("Home")
            },
            modifier = Modifier.offset(y = buttonOffsetY.value.dp),
        ) {
            Text(text = "Skip >>",
                color = Color.White)
        }
    }

}