package com.binayshaw7777.leaflekt.library

/**
 * Immutable marker model rendered by Leaflet.
 */
data class Marker(
    val id: String,
    val lat: Double,
    val lng: Double,
    val title: String?
)
