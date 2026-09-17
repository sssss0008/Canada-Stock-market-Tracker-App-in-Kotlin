package com.example.ui.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.model.CanadianMarketData

class AndroidStockBridge(
    private val onSymbolDetected: (String) -> Unit
) {
    private val mainHandler = Handler(Looper.getMainLooper())

    @JavascriptInterface
    fun onSymbolClicked(rawUrlOrSymbol: String?) {
        if (rawUrlOrSymbol.isNullOrBlank()) return
        val extracted = CanadianMarketData.extractSymbolFromUrl(rawUrlOrSymbol)
        if (!extracted.isNullOrBlank()) {
            mainHandler.post {
                onSymbolDetected(extracted)
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TradingViewWidgetView(
    htmlContent: String,
    modifier: Modifier = Modifier,
    isDark: Boolean = true,
    testTag: String = "tradingview_widget",
    onSymbolSelected: ((String) -> Unit)? = null
) {
    var isLoading by remember(htmlContent) { mutableStateOf(true) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    val currentOnSymbolSelected by rememberUpdatedState(onSymbolSelected)

    LaunchedEffect(htmlContent) {
        isLoading = true
        // Safety timeout: ensure loader dismisses even if third-party script CDN slows down
        delay(2200)
        isLoading = false
    }

    val bgColor = if (isDark) {
        android.graphics.Color.parseColor("#090D16")
    } else {
        android.graphics.Color.parseColor("#F8FAFC")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) Color(0xFF090D16) else Color(0xFFF8FAFC))
            .testTag(testTag)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(bgColor)
                    // Explicitly use software layer to bypass missing DRI /dev/dri/renderD* in cloud VM emulator
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null)

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        databaseEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        setSupportZoom(true)
                        builtInZoomControls = false
                        displayZoomControls = false
                        allowContentAccess = true
                        cacheMode = WebSettings.LOAD_DEFAULT
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    }

                    // Bridge to intercept click events from inside HTML/JS widgets
                    addJavascriptInterface(
                        AndroidStockBridge { symbol ->
                            currentOnSymbolSelected?.invoke(symbol)
                        },
                        "AndroidBridge"
                    )

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            if (newProgress >= 70) {
                                isLoading = false
                            }
                        }
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val url = request?.url?.toString() ?: return false
                            // If user tapped a stock symbol link in screener or heatmap
                            val extracted = CanadianMarketData.extractSymbolFromUrl(url)
                            if (extracted != null && currentOnSymbolSelected != null) {
                                currentOnSymbolSelected?.invoke(extracted)
                                return true // Intercept navigation and open Stock Hub in app
                            }
                            return false
                        }

                        @Deprecated("Deprecated in Java")
                        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                            if (url == null) return false
                            val extracted = CanadianMarketData.extractSymbolFromUrl(url)
                            if (extracted != null && currentOnSymbolSelected != null) {
                                currentOnSymbolSelected?.invoke(extracted)
                                return true
                            }
                            return false
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            // Don't kill display on minor resource warning
                        }
                    }

                    tag = htmlContent
                    loadDataWithBaseURL(
                        "https://www.tradingview.com",
                        htmlContent,
                        "text/html",
                        "UTF-8",
                        null
                    )
                    webViewRef = this
                }
            },
            update = { webView ->
                webView.setBackgroundColor(bgColor)
                if (webView.tag != htmlContent) {
                    webView.tag = htmlContent
                    isLoading = true
                    webView.loadDataWithBaseURL(
                        "https://www.tradingview.com",
                        htmlContent,
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            }
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Loading TSX Market Feed...",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webViewRef?.let { wv ->
                wv.stopLoading()
                wv.loadUrl("about:blank")
                wv.clearHistory()
                wv.removeAllViews()
                wv.destroy()
            }
            webViewRef = null
        }
    }
}
