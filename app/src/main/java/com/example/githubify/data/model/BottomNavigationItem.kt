package com.example.githubify.data.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
