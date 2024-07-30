package com.example.githubify

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubify.presentation.UserDataViewModel

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

@Composable
fun Home(viewModel: UserDataViewModel) {
    val user by viewModel.user.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter Username")
        Spacer(modifier = Modifier.height(16.dp))
        Searchbar(viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        UserDetails(user)
    }
}

@Composable
fun Searchbar(viewModel: UserDataViewModel) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.clickable {
                    viewModel.fetchUser(text)
                }
            )
        },
        value = text,
        label = { Text("Search") },
        onValueChange = { text = it },
    )
}