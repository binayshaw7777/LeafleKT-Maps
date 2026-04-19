package com.binayshaw7777.leaflekt.library

import android.webkit.WebView
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Stateful command gateway from Compose into the Leaflekt JavaScript runtime.
 */
class LeaflektController internal constructor() {
    private var webView: WebView? = null
    private val pendingScripts = ConcurrentLinkedQueue<String>()
    private var isMapReady = false

    internal fun setWebView(view: WebView?) {
        webView = view
    }

    internal fun notifyMapReady() {
        isMapReady = true
        while (pendingScripts.isNotEmpty()) {
            pendingScripts.poll()?.let { executeJs(it) }
        }
    }

    fun moveCamera(lat: Double, lng: Double, zoom: Double) {
        enqueueOrRun(LeaflektScriptBuilder.moveCameraScript(lat = lat, lng = lng, zoom = zoom))
    }

    fun setZoomControlsEnabled(isEnabled: Boolean) {
        enqueueOrRun(LeaflektScriptBuilder.setZoomControlsEnabledScript(isEnabled))
    }

    fun setScrollGesturesEnabled(isEnabled: Boolean) {
        enqueueOrRun(LeaflektScriptBuilder.setScrollGesturesEnabledScript(isEnabled))
    }

    fun setZoomGesturesEnabled(isEnabled: Boolean) {
        enqueueOrRun(LeaflektScriptBuilder.setZoomGesturesEnabledScript(isEnabled))
    }

    fun setMapStyle(style: LeaflektMapStyle) {
        enqueueOrRun(LeaflektScriptBuilder.setMapStyleScript(style))
    }

    internal fun addMarker(marker: LeaflektMarkerInfo) {
        enqueueOrRun(LeaflektScriptBuilder.addMarkersScript(listOf(marker)))
    }

    internal fun addMarkers(markers: List<LeaflektMarkerInfo>) {
        enqueueOrRun(LeaflektScriptBuilder.addMarkersScript(markers))
    }

    internal fun updateMarker(marker: LeaflektMarkerInfo) {
        enqueueOrRun(LeaflektScriptBuilder.updateMarkerScript(marker))
    }

    fun removeMarker(markerId: String) {
        enqueueOrRun(LeaflektScriptBuilder.removeMarkerScript(markerId))
    }

    fun clearMarkers() {
        enqueueOrRun(LeaflektScriptBuilder.clearMarkersScript())
    }

    internal fun initializeMap(
        initialLat: Double,
        initialLng: Double,
        initialZoom: Double,
        isZoomControlEnabled: Boolean,
        initialMapStyle: LeaflektMapStyle
    ) {
        enqueueOrRun(LeaflektScriptBuilder.initMapScript(initialLat, initialLng, initialZoom))
        enqueueOrRun(LeaflektScriptBuilder.setZoomControlsEnabledScript(isZoomControlEnabled))
        enqueueOrRun(LeaflektScriptBuilder.setMapStyleScript(initialMapStyle))
    }

    private fun enqueueOrRun(script: String) {
        if (isMapReady && webView != null) {
            executeJs(script)
        } else {
            pendingScripts.add(script)
        }
    }

    private fun executeJs(script: String) {
        webView?.post {
            webView?.evaluateJavascript(script, null)
        }
    }
}
