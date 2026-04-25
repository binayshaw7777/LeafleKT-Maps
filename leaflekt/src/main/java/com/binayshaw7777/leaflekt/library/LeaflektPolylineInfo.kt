package com.binayshaw7777.leaflekt.library

import androidx.compose.ui.graphics.Color

/**
 * Polyline configuration sent to the Leaflet runtime.
 *
 * `geodesic` is retained for source compatibility, but Leaflet renders the path as a normal
 * projected polyline.
 *
 * `zIndex` is applied as a best-effort draw order among vector layers.
 */
data class LeaflektPolylineInfo(
    val id: String,
    val points: List<LeaflektLatLng>,
    val clickable: Boolean = false,
    val color: Color = Color.Black,
    val geodesic: Boolean = false,
    val pattern: List<LeaflektStrokePattern>? = null,
    val visible: Boolean = true,
    val width: Float = 10f,
    val zIndex: Float = 0f,
    val alpha: Float = 1f
)
