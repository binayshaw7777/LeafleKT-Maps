package com.binayshaw7777.leaflekt.library

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewAssetLoader

@Composable
internal fun LeafletWebView(
    modifier: Modifier,
    controller: LeafletController,
    jsBridge: LeafletJsBridge,
    contentDescription: String?
) {
    val webViewState = remember { mutableStateOf<WebView?>(null) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val assetLoader = WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
                .build()

            WebView.setWebContentsDebuggingEnabled(true)
            WebView(context).apply {
                this.contentDescription = contentDescription
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.allowFileAccess = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                        if (
                            consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR ||
                            consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.WARNING
                        ) {
                            Log.w(
                                TAG,
                                "JS ${consoleMessage.messageLevel()}: ${consoleMessage.message()} " +
                                    "(${consoleMessage.sourceId()}:${consoleMessage.lineNumber()})"
                            )
                        }
                        return super.onConsoleMessage(consoleMessage)
                    }
                }
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val url = request?.url?.toString() ?: return false

                        // If it's the local map asset or a map tile, let the WebView handle it
                        if (url.startsWith("https://appassets.androidview.static/assets/") || 
                            url.startsWith("https://tile.openstreetmap.org") ||
                            url.startsWith("https://basemaps.cartocdn.com")) {
                            return false
                        }

                        // For any other links (attribution links), open in external browser
                        try {
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
                            view?.context?.startActivity(intent)
                        } catch (e: Exception) {
                            Log.e(TAG, "Failed to open external link: $url", e)
                        }
                        return true
                    }

                    override fun shouldInterceptRequest(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): WebResourceResponse? {
                        val safeRequest = request ?: return null
                        return assetLoader.shouldInterceptRequest(safeRequest.url)
                            ?: super.shouldInterceptRequest(view, safeRequest)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        val failedUrl = request?.url?.toString().orEmpty()
                        if (failedUrl.endsWith("/favicon.ico")) {
                            return
                        }
                        Log.e(TAG, "Web resource error: ${request?.url} - ${error?.description}")
                        super.onReceivedError(view, request, error)
                    }
                }
                addJavascriptInterface(jsBridge, JS_BRIDGE_NAME)
                loadUrl(MAP_ASSET_URL)
                controller.attachWebView(this)
                webViewState.value = this
            }
        },
        update = { webView ->
            controller.attachWebView(webView)
            webView.contentDescription = contentDescription
            webViewState.value = webView
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            val webView = webViewState.value ?: return@onDispose
            controller.detachWebView(webView)
            webView.removeJavascriptInterface(JS_BRIDGE_NAME)
            webView.stopLoading()
            webView.destroy()
            webViewState.value = null
        }
    }
}

private const val TAG = "LeafleKT.WebView"
