package com.binayshaw7777.leaflekt.library

import android.webkit.JavascriptInterface

internal class LeafletJsBridge(
    private val onMapReady: () -> Unit,
    private val onMarkerClick: (String) -> Unit,
    private val onMapClick: (Double, Double) -> Unit
) {
    @JavascriptInterface
    fun onMapReady() {
        onMapReady.invoke()
    }

    @JavascriptInterface
    fun onMarkerClick(id: String) {
        onMarkerClick.invoke(id)
    }

    @JavascriptInterface
    fun onMapClick(lat: Double, lng: Double) {
        onMapClick.invoke(lat, lng)
    }
}
