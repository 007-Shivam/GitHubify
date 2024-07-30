package com.example.githubify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubify.data.UserDataRepositoryImpl
import com.example.githubify.presentation.UserDataViewModel
import com.example.githubify.ui.theme.GitHubifyTheme

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home(viewModel)
                }
            }
        }
    }
}
