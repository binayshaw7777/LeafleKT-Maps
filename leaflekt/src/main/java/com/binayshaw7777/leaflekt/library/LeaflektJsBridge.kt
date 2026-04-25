package com.binayshaw7777.leaflekt.library

import android.webkit.JavascriptInterface

internal class LeaflektJsBridge(
    private val onMapReady: () -> Unit,
    private val onMapClick: (Double, Double) -> Unit,
    private val onMarkerClick: (String) -> Unit
) {
    @JavascriptInterface
    fun onMapReady() {
        onMapReady.invoke()
    }

    @JavascriptInterface
    fun onMapClick(lat: Double, lng: Double) {
        onMapClick.invoke(lat, lng)
    }

    @JavascriptInterface
    fun onMarkerClick(markerId: String) {
        onMarkerClick.invoke(markerId)
    }
}
