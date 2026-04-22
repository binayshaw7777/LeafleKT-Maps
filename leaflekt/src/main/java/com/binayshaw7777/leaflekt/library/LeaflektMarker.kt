/*
 * Copyright 2026 Binay Shaw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.binayshaw7777.leaflekt.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import java.util.UUID

/**
 * A declarative marker that can be placed inside a [LeaflektMap] content block.
 * 
 * This component follows the Jetpack Compose declarative pattern. It adds a marker to the 
 * underlying Leaflet.js map when it enters the composition and removes it when it leaves.
 * 
 * ### Usage Example:
 * ```kotlin
 * LeaflektMap {
 *     LeaflektMarker(
 *         position = LeaflektLatLng(22.5726, 88.3639),
 *         title = "Victoria Memorial",
 *         snippet = "Built between 1906 and 1921",
 *         alpha = 0.8f
 *     )
 * }
 * ```
 * 
 * **Attribution:**
 * - **Leaflet.js:** Marker rendering is powered by [L.marker](https://leafletjs.com/reference.html#marker).
 * - **OpenStreetMap:** Default marker visuals are compatible with OSM-based tile layers.
 * 
 * @param state The [LeaflektMarkerState] to be used to control or observe the marker's position.
 * @param title Optional title to be displayed in a Leaflet popup when the marker is clicked.
 * @param snippet Optional sub-text to be displayed in the popup.
 * @param visible Whether the marker is currently visible on the map.
 * @param alpha The opacity of the marker (0.0 to 1.0). Default is 1.0.
 * @param id Unique identifier for the marker. If not provided, a random UUID will be generated.
 * @param onClick A lambda invoked when the marker is clicked. Return true to consume the event.
 */
@Composable
@LeaflektMapComposable
fun LeaflektMarker(
    state: LeaflektMarkerState = rememberLeaflektMarkerState(),
    title: String? = null,
    snippet: String? = null,
    visible: Boolean = true,
    alpha: Float = 1.0f,
    id: String = remember { UUID.randomUUID().toString() },
    onClick: () -> Boolean = { false }
) {
    val controller = LocalLeaflektController.current ?: return

    // Manage marker lifecycle (Add/Remove)
    DisposableEffect(id) {
        val info = LeaflektMarkerInfo(
            id = id,
            lat = state.position.latitude,
            lng = state.position.longitude,
            title = title,
            snippet = snippet,
            visible = visible,
            alpha = alpha
        )
        
        controller.addMarker(info)
        controller.registerMarkerClick(id, onClick)

        onDispose {
            controller.unregisterMarkerClick(id)
            controller.removeMarker(id)
        }
    }

    DisposableEffect(id, onClick) {
        controller.registerMarkerClick(id, onClick)
        onDispose {
            controller.unregisterMarkerClick(id)
        }
    }

    // Manage property updates without re-creating the marker layer
    LaunchedEffect(state.position, title, snippet, visible, alpha) {
        val info = LeaflektMarkerInfo(
            id = id,
            lat = state.position.latitude,
            lng = state.position.longitude,
            title = title,
            snippet = snippet,
            visible = visible,
            alpha = alpha
        )
        controller.updateMarker(info)
    }
}

/**
 * A declarative marker that can be placed inside a [LeaflektMap] content block.
 * 
 * This is a convenience overload that takes a [LeaflektLatLng] instead of a [LeaflektMarkerState].
 * Use this when you don't need to programmatically move the marker after it's been created.
 *
 * @param position The position of the marker.
 * @param title Optional title to be displayed in a Leaflet popup when the marker is clicked.
 * @param snippet Optional sub-text to be displayed in the popup.
 * @param visible Whether the marker is currently visible on the map.
 * @param alpha The opacity of the marker (0.0 to 1.0). Default is 1.0.
 * @param id Unique identifier for the marker. If not provided, a random UUID will be generated.
 * @param onClick A lambda invoked when the marker is clicked. Return true to consume the event.
 */
@Composable
@LeaflektMapComposable
fun LeaflektMarker(
    position: LeaflektLatLng,
    title: String? = null,
    snippet: String? = null,
    visible: Boolean = true,
    alpha: Float = 1.0f,
    id: String = remember { UUID.randomUUID().toString() },
    onClick: () -> Boolean = { false }
) {
    LeaflektMarker(
        state = rememberLeaflektMarkerState(position = position),
        title = title,
        snippet = snippet,
        visible = visible,
        alpha = alpha,
        id = id,
        onClick = onClick
    )
}
