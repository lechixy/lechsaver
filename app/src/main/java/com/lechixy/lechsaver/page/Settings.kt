package com.lechixy.lechsaver.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lechixy.lechsaver.R
import com.lechixy.lechsaver.common.Route
import com.lechixy.lechsaver.components.BackButton
import com.lechixy.lechsaver.components.SettingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navController: NavController,
    onBackPressed: () -> Unit
) {
//            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
//                rememberTopAppBarState(),
//                canScroll = { true })

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        // .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = { BackButton { navController.popBackStack() } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
                //scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )

            SettingItem(
                title = stringResource(id = R.string.general),
                description = stringResource(
                    id = R.string.general_desc
                ),
                icon = Icons.Filled.Settings,
                onClick = {
                    navController.navigate(Route.SETTINGS_GENERAL)
                }
            )

            SettingItem(
                title = stringResource(id = R.string.about),
                description = stringResource(
                    id = R.string.about_desc
                ),
                icon = Icons.Filled.Info,
                onClick = {
                    navController.navigate(Route.SETTINGS_ABOUT)
                }
            )
        }
    }
}