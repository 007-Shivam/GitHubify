package com.shivam.githubify

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.shivam.githubify.activity.FollowersFollowingScreen
import com.shivam.githubify.activity.Home
import com.shivam.githubify.activity.Profile
import com.shivam.githubify.activity.UserDetails
import com.shivam.githubify.activity.sign_in.GoogleAuthUiClient
import com.shivam.githubify.activity.sign_in.SignInScreen
import com.shivam.githubify.activity.sign_in.SignInViewModel
import com.shivam.githubify.data.RetrofitInstance
import com.shivam.githubify.data.UserDataRepositoryImpl
import com.shivam.githubify.data.model.BottomNavigationItem
import com.shivam.githubify.presentation.UserDataViewModel
import com.shivam.githubify.ui.theme.GitHubifyTheme
import kotlinx.coroutines.launch

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UserDataViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(UserDataViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return UserDataViewModel(UserDataRepositoryImpl(RetrofitInstance.api)) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    })

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isSignedIn = intent.getBooleanExtra("IS_SIGNED_IN", false)

        setContent {
            GitHubifyTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination?.route

                val items = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    BottomNavigationItem(
                        title = "Profile",
                        selectedIcon = Icons.Filled.AccountCircle,
                        unselectedIcon = Icons.Outlined.AccountCircle,
                    ),
                )

                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }

                var showSignInDialog by rememberSaveable {
                    mutableStateOf(false)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (currentDestination != "SignInScreen") {
                                NavigationBar {
                                    items.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            selected = selectedItemIndex == index,
                                            onClick = {
                                                if (item.title == "Profile" && googleAuthUiClient.getSignedInUser() == null) {
                                                    showSignInDialog = true
                                                } else {
                                                    selectedItemIndex = index
                                                    navController.navigate(item.title) {
                                                        popUpTo(navController.graph.startDestinationId) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                            },
                                            label = {
                                                Text(text = item.title)
                                            },
                                            alwaysShowLabel = false,
                                            icon = {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    ) {
                        val user by viewModel.user.collectAsState()
                        val repos by viewModel.repos.collectAsState()
                        val signInViewModel: SignInViewModel by viewModels()
                        val state by signInViewModel.state.collectAsStateWithLifecycle()

                        // Show the sign-in dialog if necessary
                        if (showSignInDialog) {
                            AlertDialog(
                                onDismissRequest = { showSignInDialog = false },
                                title = { Text("Sign In Required") },
                                text = { Text("Do you want to sign in to access your profile?") },
                                confirmButton = {
                                    Button(onClick = {
                                        showSignInDialog = false
                                        navController.navigate("SignInScreen")
                                    }) {
                                        Text("Sign In")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        showSignInDialog = false
                                    }) {
                                        Text("No")
                                    }
                                }
                            )
                        }

                        NavHost(
                            navController = navController,
                            startDestination = if (isSignedIn) "Home" else "SignInScreen"
                        ) {
                            composable("Home") {
                                Home(viewModel, navController)
                            }
                            composable("Profile") {
                                Profile(
                                    userData = googleAuthUiClient.getSignedInUser(),
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            googleAuthUiClient.signOut()
                                            Toast.makeText(
                                                applicationContext,
                                                "Signed out",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            selectedItemIndex = 0
                                            navController.popBackStack(navController.graph.startDestinationId, false)
                                            navController.navigate("SignInScreen") {
                                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                )
                            }
                            composable("UserDetails") {
                                user?.let { UserDetails(it, repos, navController, viewModel) }
                            }

                            composable("SignInScreen") {
                                LaunchedEffect(key1 = Unit) {
                                    if(googleAuthUiClient.getSignedInUser() != null) {
                                        navController.navigate("Home")
                                    }
                                }

                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                                    onResult = { result ->
                                        if(result.resultCode == RESULT_OK) {
                                            lifecycleScope.launch {
                                                val signInResult = googleAuthUiClient.signInWithIntent(
                                                    intent = result.data ?: return@launch
                                                )
                                                signInViewModel.onSignInResult(signInResult)
                                            }
                                        }
                                    }
                                )

                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    if(state.isSignInSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign in successful",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.navigate("Home")
                                        signInViewModel.resetState()
                                    }
                                }

                                SignInScreen(
                                    navController,
                                    state = state,
                                    onSignInClick = {
                                        lifecycleScope.launch {
                                            val signInIntentSender = googleAuthUiClient.signIn()
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    signInIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    }
                                )
                            }

                            composable("followersScreen") {
                                FollowersFollowingScreen(
                                    title = "Followers",
                                    users = viewModel.followers.collectAsState().value.data ?: emptyList(),
                                    navController = navController,
                                    onBackClick = { navController.navigateUp() },
                                    viewModel
                                )
                            }
                            composable("followingScreen") {
                                FollowersFollowingScreen(
                                    title = "Following",
                                    users = viewModel.following.collectAsState().value.data ?: emptyList(),
                                    navController = navController,
                                    onBackClick = { navController.navigateUp() },
                                    viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
