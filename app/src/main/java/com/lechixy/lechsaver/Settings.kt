package com.lechixy.lechsaver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lechixy.lechsaver.ui.theme.LechSaverTheme


class Settings : ComponentActivity() {

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
                            CenterAlignedTopAppBar(
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
                                .padding(10.dp, 0.dp)
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Instagram Downloader",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                )
                                Util.igDownloader.forEachIndexed { index, content ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
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

                            Spacer(modifier = Modifier.height(9.dp))
                            Divider(
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                            Spacer(modifier = Modifier.height(9.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Tiktok Downloader",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                )
                                Util.ttDownloader.forEachIndexed { index, content ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
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

                            Spacer(modifier = Modifier.height(9.dp))
                            Divider(
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                            Spacer(modifier = Modifier.height(9.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Pinterest",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                )
                                Util.ptDownloader.forEachIndexed { index, content ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                ptSourceState = index
                                                editor.putInt("ptSource", index)
                                                editor.apply()
                                            }
                                    ) {
                                        RadioButton(
                                            selected = ptSourceState == index,
                                            onClick = {},
                                        )
                                        Text(text = content)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.tertiaryContainer,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .clickable {
                                        Toast
                                            .makeText(applicationContext, "💖", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    .padding(25.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "Made by lechixy",
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Just for you 💖",
                                    color = MaterialTheme.colorScheme.tertiary,
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .clickable {
                                        val browserIntent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(resources.getString(R.string.github))
                                        )
                                        startActivity(browserIntent)
                                    }
                                    .padding(15.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "${stringResource(R.string.app_name)} v${stringArrayResource(R.array.app_version).last()}"
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Updated at ${stringArrayResource(R.array.app_version_date).last()}",
                                            color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Wanna check out GitHub? Just tap here!",
                                    color = MaterialTheme.colorScheme.secondary
                                )
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