package com.lechixy.lechsaver.page.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.lechixy.lechsaver.R
import com.lechixy.lechsaver.common.Preferences
import com.lechixy.lechsaver.components.BackButton
import com.lechixy.lechsaver.components.LechDialog
import com.lechixy.lechsaver.components.PreferenceItem
import com.lechixy.lechsaver.components.PreferenceItemGroup
import com.lechixy.lechwidgets.common.Util


private const val horizontal = 8
private const val vertical = 16

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun General(
    onBackPressed: () -> Unit
) {
    var instagramSourceState by remember { mutableIntStateOf(Preferences.instagramSource) }
    var tiktokSourceState by remember { mutableIntStateOf(Preferences.tiktokSource) }
    var pinterestSourceState by remember { mutableIntStateOf(Preferences.pinterestSource) }

    var showInstagramDialog by remember { mutableStateOf(false) }
    var showTikTokDialog by remember { mutableStateOf(false) }
    var showPinterestDialog by remember { mutableStateOf(false) }

    val instagramIcon = ImageVector.vectorResource(R.drawable.instagram_icon)
    val tiktokIcon = ImageVector.vectorResource(R.drawable.tiktok_icon)
    val pinterestIcon = ImageVector.vectorResource(R.drawable.pinterest_icon)

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState(),
        canScroll = { true })

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.general)) },
                navigationIcon = {
                    BackButton { onBackPressed() }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        if (showInstagramDialog) {
            LechDialog(
                onDismissRequest = {
                    showInstagramDialog = false
                },
                title = { Text("Instagram") },
                description = { Text("Choose a downloader", textAlign = TextAlign.Center) },
                icon = { Icon(instagramIcon, null) },
                text = {
                    Column {
                        Util.igDownloader.forEachIndexed { index, content ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        instagramSourceState = index
                                        Preferences.instagramSource = index
                                        showInstagramDialog = false
                                    }
                            ) {
                                RadioButton(
                                    selected = instagramSourceState == index,
                                    onClick = {}
                                )
                                Text(text = content)
                            }
                        }
                    }
                }
            )
        }
        if (showTikTokDialog) {
            LechDialog(
                onDismissRequest = {
                    showTikTokDialog = false
                },
                title = { Text("TikTok") },
                description = { Text("Choose a downloader", textAlign = TextAlign.Center) },
                icon = { Icon(tiktokIcon, null) },
                text = {
                    Column {
                        Util.ttDownloader.forEachIndexed { index, content ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        tiktokSourceState = index
                                        Preferences.tiktokSource = index
                                        showTikTokDialog = false
                                    }
                            ) {
                                RadioButton(
                                    selected = tiktokSourceState == index,
                                    onClick = {}
                                )
                                Text(text = content)
                            }
                        }
                    }
                }
            )
        }
        if (showPinterestDialog) {
            LechDialog(
                onDismissRequest = {
                    showPinterestDialog = false
                },
                title = { Text("Pinterest") },
                description = { Text("Choose a downloader", textAlign = TextAlign.Center) },
                icon = { Icon(pinterestIcon, null) },
                text = {
                    Column {
                        Util.ptDownloader.forEachIndexed { index, content ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        pinterestSourceState = index
                                        Preferences.pinterestSource = index
                                        showPinterestDialog = false
                                    }
                            ) {
                                RadioButton(
                                    selected = pinterestSourceState == index,
                                    onClick = {}
                                )
                                Text(text = content)
                            }
                        }
                    }
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                PreferenceItemGroup {
                    PreferenceItem(
                        title = "Instagram",
                        description = Util.igDownloader[instagramSourceState],
                        descriptionColor = MaterialTheme.colorScheme.primary,
                        icon = instagramIcon,
                        iconColor = MaterialTheme.colorScheme.primary,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                tint = MaterialTheme.colorScheme.outline,
                                contentDescription = "Settings"
                            )
                        },
                        onClick = {
                            showInstagramDialog = true
                        }
                    )
                    PreferenceItem(
                        title = "TikTok",
                        description = Util.ttDownloader[tiktokSourceState],
                        descriptionColor = MaterialTheme.colorScheme.primary,
                        icon = tiktokIcon,
                        iconColor = MaterialTheme.colorScheme.primary,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                tint = MaterialTheme.colorScheme.outline,
                                contentDescription = "Settings"
                            )
                        },
                        onClick = {
                            showTikTokDialog = true
                        }
                    )
                    PreferenceItem(
                        title = "Pinterest",
                        description = Util.ptDownloader[pinterestSourceState],
                        descriptionColor = MaterialTheme.colorScheme.primary,
                        icon = pinterestIcon,
                        iconColor = MaterialTheme.colorScheme.primary,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                tint = MaterialTheme.colorScheme.outline,
                                contentDescription = "Settings"
                            )
                        },
                        onClick = {
                            showPinterestDialog = true
                        }
                    )
                }
            }
        }
    }
}