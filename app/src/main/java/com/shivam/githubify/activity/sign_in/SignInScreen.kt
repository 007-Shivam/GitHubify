package com.shivam.githubify.activity.sign_in

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SignInScreen(
    navController: NavHostController,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onSignInClick) {
            Text(text = "Sign in")
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    TextButton(onClick = {
        navController.navigate("Home")
    }) {
        Text(text = "Skip SignIn",
            color = Color.White)
    }
}