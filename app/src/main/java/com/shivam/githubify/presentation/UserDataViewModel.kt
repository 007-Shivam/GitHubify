package com.shivam.githubify.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.githubify.data.UserDataRepository
import com.shivam.githubify.data.model.UserData
import com.shivam.githubify.data.Result
import com.shivam.githubify.data.model.FollowingFollowersData
import com.shivam.githubify.data.model.RepoData
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

    private val _repos = MutableStateFlow<List<RepoData>>(emptyList())
    val repos: StateFlow<List<RepoData>> = _repos.asStateFlow()

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private val _followers = MutableStateFlow<Result<List<FollowingFollowersData>>>(Result.Success(emptyList()))
    val followers: StateFlow<Result<List<FollowingFollowersData>>> = _followers

    private val _following = MutableStateFlow<Result<List<FollowingFollowersData>>>(Result.Success(emptyList()))
    val following: StateFlow<Result<List<FollowingFollowersData>>> = _following


    private var isReposFetched = false

    fun clearUserData() {
        _user.value = null
        _repos.value = emptyList()
        isReposFetched = false
    }

    fun fetchUser(username: String) {
        viewModelScope.launch {
            _loading.value = true
            clearUserData()
            userRepository.getUser(username).collectLatest { result ->
                _loading.value = false
                when (result) {
                    is Result.Error -> {
                        _user.value = null
                        _error.value = "User not found."
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { user ->
                            _user.value = user
                            _error.value = ""
                        } ?: run {
                            _user.value = null
                            _error.value = "User not found."
                        }
                    }
                }
            }
        }
    }

    fun fetchRepos(username: String) {
        if (isReposFetched) return

        viewModelScope.launch {
            userRepository.getRepos(username).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _repos.value = emptyList()
                        _error.value = "Error loading repos."
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        _repos.value = result.data ?: emptyList()
                        isReposFetched = true
                    }
                }
            }
        }
    }

    fun fetchFollowers(username: String) {
        viewModelScope.launch {
            userRepository.getFollowers(username).collect { result ->
                _followers.value = result
            }
        }
    }

    fun fetchFollowing(username: String) {
        viewModelScope.launch {
            userRepository.getFollowing(username).collect { result ->
                _following.value = result
            }
        }
    }
}
