package com.binayshaw7777.leaflekt.library

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
     * Shows or hides the Leaflet zoom controls.
     */
    fun setZoomControlsEnabled(isEnabled: Boolean) {
        enqueueOrRun(LeafletScriptBuilder.setZoomControlsEnabledScript(isEnabled))
    }

    /**
     * Replaces the active tile layer with one of the built-in map styles.
     */
    fun setMapStyle(style: LeafletMapStyle) {
        enqueueOrRun(LeafletScriptBuilder.setMapStyleScript(style))
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

    internal fun initializeMap(
        initialLat: Double,
        initialLng: Double,
        initialZoom: Double,
        isZoomControlEnabled: Boolean,
        initialMapStyle: LeafletMapStyle
    ) {
        enqueueOrRun(LeafletScriptBuilder.initMapScript(initialLat, initialLng, initialZoom))
        enqueueOrRun(LeafletScriptBuilder.setZoomControlsEnabledScript(isZoomControlEnabled))
        enqueueOrRun(LeafletScriptBuilder.setMapStyleScript(initialMapStyle))
    }

    internal fun attachWebView(webView: WebView) {
        this.webView = webView
        flushPendingScripts()
    }

    internal fun detachWebView(webView: WebView) {
        if (this.webView === webView) {
            this.webView = null
            isMapReady = false
        }
    }

    internal fun notifyMapReady() {
        isMapReady = true
        flushPendingScripts()
    }

    private fun enqueueOrRun(script: String) {
        if (isMapReady && webView != null) {
            jsExecutor.runJS(script)
            return
        }
        pendingScripts.addLast(script)
    }

    private fun flushPendingScripts() {
        if (!isMapReady || webView == null) {
            return
        }
        while (pendingScripts.isNotEmpty()) {
            jsExecutor.runJS(pendingScripts.removeFirst())
        }
    }
}
