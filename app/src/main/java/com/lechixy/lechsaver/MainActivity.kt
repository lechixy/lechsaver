package com.lechixy.lechsaver

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.lechixy.lechsaver.ui.theme.LechSaverTheme
import java.net.URL


class MainActivity : ComponentActivity()
{
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext
        val sharedPreferences: SharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val ttDownloader = listOf("https://snaptik.app/", "https://ssstik.io/en")
        val igDownloader = listOf("https://snapinsta.app/", "https://saveinsta.app/")
        val ptDownloader = listOf("https://pinterestdownloader.com/")

        // Check if the intent action is SEND
        if (Intent.ACTION_SEND == intent.action) {
            // Get the URL from the intent
            val sharedUri = intent.getCharSequenceExtra(Intent.EXTRA_TEXT)
            if (sharedUri != null) {
                val url = sharedUri.toString()

                Toast.makeText(context, "Copied shared link, paste it!", Toast.LENGTH_SHORT).show()

                val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Url for save", url)
                clipboardManager.setPrimaryClip(clip)

                var downloader: String? = null
                var source: Int

                val urlObject = URL(url)

                if(urlObject.host.contains("www.instagram.com") || urlObject.host.contains("instagram.com")){
                    source = sharedPreferences.getInt("igSource", 0)
                    downloader = igDownloader[source]
                }
                if(urlObject.host.contains("vm.tiktok.com") || urlObject.host.contains("www.tiktok.com")){
                    source = sharedPreferences.getInt("ttSource", 0)
                    downloader = ttDownloader[source]
                }
                if(urlObject.host.contains("pin.it")){
                    source = sharedPreferences.getInt("ptSource", 0)
                    downloader = ptDownloader[source]
                }

                finish()

                downloader.let {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ContextCompat.startActivity(context, browserIntent, null)
                }
            }
        }

        setContent {
            LechSaverTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_in, androidx.appcompat.R.anim.abc_fade_out);
                            }) {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                            }
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
                            ){
                                Text("Just share your link")
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(10.dp)
                            ){
                                Text("we'll take care of other things")
                            }
                        }
                    }
                }
            }
        }
    }
}