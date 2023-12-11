package com.lechixy.lechsaver

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.chibatching.kotpref.Kotpref
import com.lechixy.lechsaver.common.Preferences
import com.lechixy.lechsaver.ui.theme.LechSaverTheme
import com.lechixy.lechwidgets.common.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class QuickDownload : ComponentActivity() {
    private var url: String = ""
    private var downloader: String = ""
    private var downloaderNum: Int = 0
    private var repeatedRunnable: Runnable? = null
    private fun handleShareIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SEND -> {
                val text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT)
                url = text.toString()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, true)
        //WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
        //    false
//        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
//            v.setPadding(0)
//            insets
//        }

        window.run {
            setBackgroundDrawable(ColorDrawable(0))
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            //setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        }

        handleShareIntent(intent)

        // Preferences
        Kotpref.init(applicationContext)

        if (url.isEmpty()) {
            return this.finish()
        }
        // Pinterest sharing url with emoji
        else if (url.contains("\uD83D\uDC40")) {
            url = url.split("\uD83D\uDC40")[1]
        }

        var source: Int
        val urlObject = URL(url)
        if (urlObject.host.contains("www.instagram.com") || urlObject.host.contains("instagram.com")) {
            source = Preferences.instagramSource
            downloader = Util.igDownloader[source]
            downloaderNum = source
        }
        if (urlObject.host.contains("vm.tiktok.com") || urlObject.host.contains("www.tiktok.com")) {
            source = Preferences.tiktokSource
            downloader = Util.ttDownloader[source]
            downloaderNum = source
        }
        if (urlObject.host.contains("pin.it")) {
            source = Preferences.pinterestSource
            downloader = Util.ptDownloader[source]
            downloaderNum = source
        }

        if (downloader.isEmpty()) {
            this.finish()
        }

        Toast.makeText(applicationContext, "Copied shared link, paste it!", Toast.LENGTH_SHORT)
            .show()

        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("LechSaver", url)
        clipboardManager.setPrimaryClip(clip)

        setContent {
            //val scope = rememberCoroutineScope()
            LechSaverTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                var showDialog by remember { mutableStateOf(true) }
                val scope = rememberCoroutineScope()
                val drawerState =
                    rememberModalBottomSheetState(
                        skipPartiallyExpanded = true,
                        //initialValue = ModalBottomSheetValue.Expanded,
                    )

                window.statusBarColor = MaterialTheme.colorScheme.primary.toArgb()

                LaunchedEffect(drawerState.currentValue, showDialog) {
                    if (!showDialog)
                        this@QuickDownload.finish()
                }

                if (showDialog) {
                    ModalBottomSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f),
                        shape = RoundedCornerShape(
                            topStart = 28.0.dp,
                            topEnd = 28.0.dp,
                            bottomEnd = 0.0.dp,
                            bottomStart = 0.0.dp
                        ),
                        sheetState = drawerState,
                        containerColor = MaterialTheme.colorScheme.background,
                        //contentColor = MaterialTheme.colorScheme.background,
                        onDismissRequest = {
                            showDialog = false
                        },
                    ) {
                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize(),
                            snackbarHost = {
                                SnackbarHost(
                                    hostState = snackbarHostState
                                ){
                                    Snackbar(
                                        snackbarData = it,
                                        containerColor = MaterialTheme.colorScheme.background,
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            },
                            containerColor = Color.Transparent
                        ) { it ->
                            it
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                var loadingPage by remember { mutableStateOf(true) }
                                if (loadingPage) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.width(64.dp),
                                        color = MaterialTheme.colorScheme.secondary,
                                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                    )
                                }

//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(10.dp),
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Text(
//                                    "Download video",
//                                )
//                            }
                                AndroidView(
                                    modifier = Modifier
                                        .verticalScroll(rememberScrollState())
                                        .fillMaxSize(),
                                    factory = {
                                        WebView(this@QuickDownload).apply {
                                            layoutParams = ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.MATCH_PARENT
                                            )

                                            this.settings.javaScriptEnabled = true
                                            this.background = null

                                            setDownloadListener { url, _, contentDisposition, mimeType, _ ->
                                                val request =
                                                    DownloadManager.Request(
                                                        Uri.parse(url)
                                                    )
                                                request.setNotificationVisibility(
                                                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                                                ) //Notify client once download is completed!
                                                val fileName = Util.createFilenameForDownload(
                                                    downloader,
                                                    contentDisposition,
                                                    mimeType
                                                )
                                                request.setDestinationInExternalPublicDir(
                                                    Environment.DIRECTORY_DOWNLOADS,
                                                    fileName
                                                )
                                                val dm =
                                                    getSystemService(
                                                        DOWNLOAD_SERVICE
                                                    ) as DownloadManager
                                                dm.enqueue(request)

                                                scope.launch(Dispatchers.Main) {
                                                    snackbarHostState.showSnackbar(
                                                        "Downloading $fileName",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                }
                                            }

                                            webViewClient = object : WebViewClient() {
                                                override fun onPageStarted(
                                                    view: WebView?,
                                                    url: String?,
                                                    favicon: Bitmap?
                                                ) {
                                                    super.onPageStarted(view, url, favicon)
                                                    loadingPage = true
                                                    Log.i("LECH", "started")
                                                }

                                                override fun onPageFinished(
                                                    view: WebView?,
                                                    url: String?
                                                ) {
                                                    super.onPageFinished(view, url)
                                                    loadingPage = false
                                                    Log.i("LECH", "finish")

                                                    var webURLObject: URL? = null
                                                    if (url != "about:blank") {
                                                        webURLObject = URL(url)
                                                    }

                                                    Log.i("LECH", "ne")
                                                    if (
                                                        webURLObject != null &&
                                                        webURLObject.host.contains(webURLObject.host)
                                                    ) {
                                                        // We need to paste Video Url to input element
                                                        var inputURLValue: String = ""
                                                        if (downloader == Util.igDownloader[downloaderNum]) {
                                                            inputURLValue =
                                                                "${
                                                                    Util.getInstagramScripts(
                                                                        downloaderNum
                                                                    )[0]
                                                                } = '${this@QuickDownload.url}'"
                                                        } else if (downloader == Util.ttDownloader[downloaderNum]) {
                                                            inputURLValue =
                                                                "${
                                                                    Util.getTiktokScripts(
                                                                        downloaderNum
                                                                    )[0]
                                                                } = '${this@QuickDownload.url}'"
                                                        } else if (downloader == Util.ptDownloader[downloaderNum]) {
                                                            inputURLValue =
                                                                "${
                                                                    Util.getPinterestScripts(
                                                                        downloaderNum
                                                                    )[0]
                                                                } = '${this@QuickDownload.url}'"
                                                        }
                                                        evaluateJavascript(inputURLValue) {}
                                                    }

                                                    val handler = Handler()
                                                    var isConverted = false
                                                    repeatedRunnable = Runnable {
                                                        if (isConverted) {
                                                            return@Runnable handler.removeCallbacks(
                                                                repeatedRunnable!!
                                                            )
                                                        }
                                                        Log.i("LECH", "worked")
                                                        if (downloader == Util.igDownloader[downloaderNum]) {
                                                            evaluateJavascript(
                                                                Util.getInstagramScripts(downloaderNum)[1]
                                                            ) { downloadLength ->
                                                                if (downloadLength.equals("1")) {
                                                                    evaluateJavascript(
                                                                        Util.getInstagramScripts(
                                                                            downloaderNum
                                                                        )[2]
                                                                    ) { videoUrl ->
                                                                        if (!videoUrl.equals("null")) {
                                                                            isConverted = true
                                                                            val downloadLink =
                                                                                videoUrl.substring(
                                                                                    1,
                                                                                    videoUrl.length - 1
                                                                                )
                                                                            loadUrl(downloadLink)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else if (downloader == Util.ttDownloader[downloaderNum]) {
                                                            evaluateJavascript(
                                                                Util.getTiktokScripts(
                                                                    downloaderNum
                                                                )[1]
                                                            ) {
                                                                if (!it.equals("null")) {
                                                                    isConverted = true
                                                                    val downloadLink =
                                                                        it.substring(1, it.length - 1)
                                                                    loadUrl(downloadLink)
                                                                }
                                                            }
                                                        }
                                                        // Don't do pinterest because i don't want...
                                                        // download the content auto

                                                        handler.postDelayed(repeatedRunnable!!, 500)
                                                    }

                                                    handler.postDelayed(repeatedRunnable!!, 500)
                                                }
                                            }

                                            loadUrl(this@QuickDownload.downloader)
                                        }
                                    })
                            }

                        }
                    }
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
    }
}