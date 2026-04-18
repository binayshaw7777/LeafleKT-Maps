package com.binayshaw7777.leaflekt.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier

/**
 * Compose API for rendering a Leaflet map backed by WebView and JavaScript bridge.
 */
@Composable
fun LeafletMap(
    modifier: Modifier = Modifier,
    onReady: (LeafletController) -> Unit = {},
    onMapClick: ((Double, Double) -> Unit)? = null,
    onMarkerClick: ((String) -> Unit)? = null,
    initialCenterLat: Double = 22.5726,
    initialCenterLng: Double = 88.3639,
    initialZoom: Double = 12.0
) {
    val controller = remember { LeafletController() }
    val onReadyState = rememberUpdatedState(onReady)
    val onMapClickState = rememberUpdatedState(onMapClick)
    val onMarkerClickState = rememberUpdatedState(onMarkerClick)

    val jsBridge = remember(controller) {
        LeafletJsBridge(
            onMapReady = { controller.notifyMapReady() },
            onMarkerClick = { id -> onMarkerClickState.value?.invoke(id) },
            onMapClick = { lat, lng -> onMapClickState.value?.invoke(lat, lng) }
        )
    }

    LaunchedEffect(controller) {
        onReadyState.value(controller)
    }

    LaunchedEffect(controller, initialCenterLat, initialCenterLng, initialZoom) {
        controller.initializeMap(
            initialLat = initialCenterLat,
            initialLng = initialCenterLng,
            initialZoom = initialZoom
        )
    }

    LeafletWebView(
        modifier = modifier,
        controller = controller,
        jsBridge = jsBridge
    )
}
