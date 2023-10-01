package com.lechixy.lechsaver

import android.os.Bundle
import android.view.View.OnClickListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lechixy.lechsaver.ui.theme.LechSaverTheme


class SettingsActivity : ComponentActivity() {

    val ttDownloaders = listOf("https://snaptik.app/", "https://ssstik.io/")
    val igDownloaders = listOf("https://snapinsta.app/", "https://saveinsta.app/")
    val ptDownloaders = listOf("https://pinterestdownloader.com/")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val igSource = sharedPreferences.getInt("igSource", 0)
        val ttSource = sharedPreferences.getInt("ttSource", 0)
        val ptSource = sharedPreferences.getInt("ptSource", 0)
        val editor = sharedPreferences.edit()

        setContent {
            var igSourceState by remember { mutableStateOf(igSource) }
            var ttSourceState by remember { mutableStateOf(ttSource) }
            var ptSourceState by remember { mutableStateOf(ptSource) }

            LechSaverTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text("Settings") },
                                colors = TopAppBarDefaults.mediumTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = MaterialTheme.colorScheme.background
                                )
                            )
                        },

                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            Divider(
                                thickness = 20.dp,
                                color = MaterialTheme.colorScheme.background
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(20.dp))
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Instagram Downloader",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                igDownloaders.forEachIndexed { index, content ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                            .clickable {
                                            igSourceState = index
                                            editor.putInt("igSource", index)
                                            editor.apply()
                                        }
                                    ) {
                                        RadioButton(
                                            selected = igSourceState == index,
                                            onClick = {}
                                        )
                                        Text(text = content)
                                    }
                                }
                            }
                            Divider(
                                thickness = 20.dp,
                                color = MaterialTheme.colorScheme.background
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(20.dp))
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Tiktok Downloader",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                ttDownloaders.forEachIndexed { index, content ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                            .clickable {
                                            ttSourceState = index
                                            editor.putInt("ttSource", index)
                                            editor.apply()
                                        }
                                    ) {
                                        RadioButton(
                                            selected = ttSourceState == index,
                                            onClick = {}
                                        )
                                        Text(text = content)
                                    }
                                }
                            }
                            Divider(
                                thickness = 20.dp,
                                color = MaterialTheme.colorScheme.background
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(20.dp))
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Pinterest Downloader",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                ptDownloaders.forEachIndexed { index, content ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                            .clickable {
                                                ptSourceState = index
                                                editor.putInt("ptSource", index)
                                                editor.apply()
                                            }
                                    ) {
                                        RadioButton(
                                            selected = ptSourceState == index,
                                            onClick = {}
                                        )
                                        Text(text = content)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, R.anim.slide_out)
    }
}