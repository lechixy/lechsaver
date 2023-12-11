package com.lechixy.lechsaver.page

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lechixy.lechsaver.R
import com.lechixy.lechsaver.components.BackButton
import com.lechixy.lechsaver.components.ColoredAppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

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
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    ColoredAppText()
                    Spacer(modifier = Modifier.height(40.dp))
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
                                    .makeText(context, "💖", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .padding(25.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Made by lechixy",
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Just for you 💖",
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
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
                                    Uri.parse(context.resources.getString(R.string.github))
                                )
                                context.startActivity(browserIntent)
                            }
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Version ${
                                stringArrayResource(
                                    R.array.app_version
                                ).last()
                            }"
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