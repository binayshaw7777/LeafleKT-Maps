package com.binayshaw7777.leaflekt.library

import android.util.Log
import android.webkit.WebView

/**
 * Stateful command gateway from Compose into the Leaflet JavaScript runtime.
 * Commands issued before map readiness are queued and replayed once the map reports ready.
 */
class LeafletController internal constructor() {
    private var webView: WebView? = null
    private val jsExecutor = JsExecutor { webView }
    private val pendingScripts = ArrayDeque<String>()
    private var isMapReady = false

    /**
     * Moves the map camera to a new latitude, longitude, and zoom level.
     */
    fun setCenter(lat: Double, lng: Double, zoom: Double) {
        enqueueOrRun(LeafletScriptBuilder.moveCameraScript(lat = lat, lng = lng, zoom = zoom))
    }

    /**
     * Adds a single marker to the map.
     */
    fun addMarker(marker: Marker) {
        addMarkers(listOf(marker))
    }

    /**
     * Adds multiple markers in one bridge call.
     */
    fun addMarkers(markers: List<Marker>) {
        if (markers.isEmpty()) {
            return
        }
        enqueueOrRun(LeafletScriptBuilder.addMarkersScript(markers))
    }

    /**
     * Clears all markers currently rendered on map.
     */
    fun clearMarkers() {
        enqueueOrRun(LeafletScriptBuilder.clearMarkersScript())
    }

    internal fun initializeMap(initialLat: Double, initialLng: Double, initialZoom: Double) {
        Log.d(TAG, "initializeMap lat=$initialLat lng=$initialLng zoom=$initialZoom")
        enqueueOrRun(LeafletScriptBuilder.initMapScript(initialLat, initialLng, initialZoom))
    }

    internal fun attachWebView(webView: WebView) {
        Log.d(TAG, "attachWebView width=${webView.width} height=${webView.height}")
        this.webView = webView
        flushPendingScripts()
    }

    internal fun detachWebView(webView: WebView) {
        if (this.webView === webView) {
            Log.d(TAG, "detachWebView")
            this.webView = null
            isMapReady = false
        }
    }

    internal fun notifyMapReady() {
        Log.d(TAG, "notifyMapReady pendingScripts=${pendingScripts.size}")
        isMapReady = true
        flushPendingScripts()
    }

    private fun enqueueOrRun(script: String) {
        Log.d(TAG, "enqueueOrRun ready=$isMapReady hasWebView=${webView != null} script=$script")
        if (isMapReady && webView != null) {
            jsExecutor.runJS(script)
            return
        }
        pendingScripts.addLast(script)
    }

    private fun flushPendingScripts() {
        if (!isMapReady || webView == null) {
            Log.d(TAG, "flushPendingScripts skipped ready=$isMapReady hasWebView=${webView != null}")
            return
        }
        Log.d(TAG, "flushPendingScripts count=${pendingScripts.size}")
        while (pendingScripts.isNotEmpty()) {
            jsExecutor.runJS(pendingScripts.removeFirst())
        }
    }

    private companion object {
        const val TAG = "LeafleKT.Controller"
    }
}
