package com.lechixy.lechsaver.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lechixy.lechsaver.components.ColoredAppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navigateToSettings: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
//            floatingActionButton = {
//                FloatingActionButton(onClick = navigateToSettings) {
//                    Icon(
//                        imageVector = Icons.Default.Settings,
//                        contentDescription = "Settings"
//                    )
//                }
//            },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    ColoredAppText()
                },
                actions = {
                    IconButton(
                        onClick = { navigateToSettings() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    ) { it ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp)
            ) {
                Text("Just share your link")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(10.dp)
            ) {
                Text("we'll take care of other things")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(10.dp)
            ) {
                Text("<3")
            }
        }
    }
}