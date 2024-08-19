package com.shivam.githubify.activity

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.Identity
import com.shivam.githubify.LoadingAnimation
import com.shivam.githubify.R
import com.shivam.githubify.activity.sign_in.GoogleAuthUiClient
import com.shivam.githubify.presentation.UserDataViewModel
import kotlin.system.exitProcess

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(viewModel: UserDataViewModel, navController: NavHostController) {
    val context = LocalContext.current

    val user by viewModel.currentUser.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val repos by viewModel.repos.collectAsState()
    // Check if the user is signed in
    val googleAuthUiClient = remember {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    val isSignedIn = googleAuthUiClient.getSignedInUser() != null

    BackHandler {
        viewModel.clearUserData()

        if (isSignedIn) {
            exitProcess(0)
        } else {
            navController.navigate("SignInScreen")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        } else {
            if (user == null) {
                Searchbar(viewModel, error) { username ->
                    viewModel.pushUser(username)
                    viewModel.fetchUser(username)
                }
            } else {
                LaunchedEffect(user) {
                    navController.navigate("UserDetails")
                }
            }
        }
    }
}

@Composable
fun Searchbar(viewModel: UserDataViewModel, error: String, onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Spacer(modifier = Modifier.height(100.dp))

    Text(
        text = "Enter the\nGithub\nUsername",
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.zain_extrabold)),
        fontSize = 60.sp,
        lineHeight = 70.sp,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(25.dp))

    OutlinedTextField(
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        value = text,
        label = { Text("Search") },
        onValueChange = { text = it },
        maxLines = 1
    )

    Spacer(modifier = Modifier.height(15.dp))

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onSearch(text)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.search))
        }
    }

    Spacer(modifier = Modifier.height(15.dp))

    if (error.isNotEmpty()) {
        ErrorMessage(error)
    }
}

@Composable
fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}