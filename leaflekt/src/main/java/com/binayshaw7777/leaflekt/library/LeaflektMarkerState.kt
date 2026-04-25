package com.binayshaw7777.leaflekt.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * A state object that can be hoisted to control and observe the marker state.
 *
 * This implementation follows the Google Maps Compose pattern to provide a familiar API
 * for Kotlin developers using Leaflekt.
 *
 * @param position the initial marker position
 */
class LeaflektMarkerState(position: LeaflektLatLng = LeaflektLatLng(0.0, 0.0)) {
    /**
     * Current position of the marker.
     *
     * This property is backed by Compose state. It can be updated by the API user
     * to move the marker on the map.
     */
    var position: LeaflektLatLng by mutableStateOf(position)

    /**
     * Shows the info window for the underlying marker.
     */
    fun showInfoWindow() {
        // Implementation for future: Trigger L.marker.openPopup() via bridge
    }

    /**
     * Hides the info window for the underlying marker.
     */
    fun hideInfoWindow() {
        // Implementation for future: Trigger L.marker.closePopup() via bridge
    }

    companion object {
        /**
         * The default saver implementation for [LeaflektMarkerState].
         */
        val Saver: Saver<LeaflektMarkerState, *> = Saver(
            save = { listOf(it.position.latitude, it.position.longitude) },
            restore = { 
                LeaflektMarkerState(LeaflektLatLng(it[0], it[1]))
            }
        )
    }
}

/**
 * Creates and [rememberSaveable]s a [LeaflektMarkerState].
 *
 * @param key optional key for the saved state
 * @param position the initial marker position
 */
@Composable
fun rememberLeaflektMarkerState(
    key: String? = null,
    position: LeaflektLatLng = LeaflektLatLng(0.0, 0.0)
): LeaflektMarkerState = rememberSaveable(key = key, saver = LeaflektMarkerState.Saver) {
    LeaflektMarkerState(position)
}
