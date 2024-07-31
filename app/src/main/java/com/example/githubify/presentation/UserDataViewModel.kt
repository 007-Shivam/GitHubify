package com.example.githubify.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubify.data.UserDataRepository
import com.example.githubify.data.model.UserData
import com.example.githubify.data.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

class UserDataViewModel(
    private val userRepository: UserDataRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user.asStateFlow()

    private val _error = MutableStateFlow<String>("")
    val error: StateFlow<String> = _error.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun fetchUser(username: String) {
        viewModelScope.launch {
            userRepository.getUser(username).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _user.value = null // Clear the current user
                        _error.value = "User not found." // Set error message
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { user ->
                            _user.update { user }
                            _error.value = "" // Clear any previous error message
                        } ?: run {
                            _user.value = null
                            _error.value = "User not found."
                        }
                    }
                }
            }
        }
    }
    fun clearUserData() {
        _user.value = null
    }
}
