package com.binayshaw7777.leaflekt.library

import android.util.Log
import android.webkit.JavascriptInterface

internal class LeafletJsBridge(
    private val onMapReady: () -> Unit,
    private val onMarkerClick: (String) -> Unit,
    private val onMapClick: (Double, Double) -> Unit
) {
    @JavascriptInterface
    fun onMapReady() {
        Log.d(TAG, "onMapReady")
        onMapReady.invoke()
    }

    @JavascriptInterface
    fun onMarkerClick(id: String) {
        Log.d(TAG, "onMarkerClick id=$id")
        onMarkerClick.invoke(id)
    }

    @JavascriptInterface
    fun onMapClick(lat: Double, lng: Double) {
        Log.d(TAG, "onMapClick lat=$lat lng=$lng")
        onMapClick.invoke(lat, lng)
    }

    private companion object {
        const val TAG = "LeafleKT.Bridge"
    }
}
