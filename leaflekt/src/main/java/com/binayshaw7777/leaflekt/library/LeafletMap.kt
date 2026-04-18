package com.binayshaw7777.leaflekt.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Compose API for rendering a Leaflet map backed by WebView and JavaScript bridge.
 */
@Composable
fun LeafletMap(
    modifier: Modifier = Modifier,
    cameraPositionState: LeafletCameraPositionState = rememberLeafletCameraPositionState(),
    contentDescription: String? = null,
    properties: LeafletMapProperties = DefaultLeafletMapProperties,
    uiSettings: LeafletMapUiSettings = DefaultLeafletMapUiSettings,
    onMapLoaded: (() -> Unit)? = null,
    onReady: ((LeafletController) -> Unit)? = null,
    onMapClick: ((LeafletLatLng) -> Unit)? = null,
    onMarkerClick: ((String) -> Unit)? = null,
    content: @Composable @LeafletMapComposable () -> Unit = {},
) {
    val controller = remember { LeafletController() }
    var hasReportedReady by remember { mutableStateOf(false) }
    val onReadyState = rememberUpdatedState(onReady)
    val onMapLoadedState = rememberUpdatedState(onMapLoaded)
    val onMapClickState = rememberUpdatedState(onMapClick)
    val onMarkerClickState = rememberUpdatedState(onMarkerClick)

    val jsBridge = remember(controller) {
        LeafletJsBridge(
            onMapReady = {
                controller.notifyMapReady()
                if (!hasReportedReady) {
                    hasReportedReady = true
                    onReadyState.value?.invoke(controller)
                    onMapLoadedState.value?.invoke()
                }
            },
            onMarkerClick = { id -> onMarkerClickState.value?.invoke(id) },
            onMapClick = { lat, lng ->
                onMapClickState.value?.invoke(
                    LeafletLatLng(latitude = lat, longitude = lng)
                )
            }
        )
    }

    LaunchedEffect(controller) {
        controller.initializeMap(
            initialLat = cameraPositionState.position.target.latitude,
            initialLng = cameraPositionState.position.target.longitude,
            initialZoom = cameraPositionState.position.zoom,
            isZoomControlEnabled = uiSettings.isZoomControlEnabled,
            initialMapStyle = properties.mapStyle
        )
    }

    LaunchedEffect(cameraPositionState.position) {
        controller.setCenter(
            lat = cameraPositionState.position.target.latitude,
            lng = cameraPositionState.position.target.longitude,
            zoom = cameraPositionState.position.zoom
        )
    }

    LaunchedEffect(properties) {
        controller.setMapStyle(properties.mapStyle)
    }

    LaunchedEffect(uiSettings) {
        controller.setZoomControlsEnabled(uiSettings.isZoomControlEnabled)
    }

    Box(modifier = modifier) {
        LeafletWebView(
            modifier = Modifier.fillMaxSize(),
            controller = controller,
            jsBridge = jsBridge,
            contentDescription = contentDescription
        )

        content()
    }
}
