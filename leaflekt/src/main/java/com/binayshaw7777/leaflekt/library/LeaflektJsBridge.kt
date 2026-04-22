package com.binayshaw7777.leaflekt.library

import android.webkit.JavascriptInterface

internal class LeaflektJsBridge(
    private val onMapReady: () -> Unit,
    private val onMapClick: (Double, Double) -> Unit,
    private val onMarkerClick: (String) -> Unit,
    private val onPolylineClick: (String) -> Unit,
    private val onPolygonClick: (String) -> Unit,
    private val onCircleClick: (String) -> Unit
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

    @JavascriptInterface
    fun onPolylineClick(polylineId: String) {
        onPolylineClick.invoke(polylineId)
    }

    @JavascriptInterface
    fun onPolygonClick(polygonId: String) {
        onPolygonClick.invoke(polygonId)
    }

    @JavascriptInterface
    fun onCircleClick(circleId: String) {
        onCircleClick.invoke(circleId)
    }
}
