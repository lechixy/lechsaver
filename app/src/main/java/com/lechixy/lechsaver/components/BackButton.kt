package com.lechixy.lechsaver.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(modifier = Modifier, onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = "Back",
        )
    }
}