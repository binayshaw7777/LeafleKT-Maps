package com.binayshaw7777.leaflekt.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class LeafletLatLng(
    val latitude: Double,
    val longitude: Double
)

data class LeafletCameraPosition(
    val target: LeafletLatLng,
    val zoom: Double
)

@Stable
class LeafletCameraPositionState(
    initialPosition: LeafletCameraPosition
) {
    var position by mutableStateOf(initialPosition)
        private set

    fun move(position: LeafletCameraPosition) {
        this.position = position
    }

    fun move(target: LeafletLatLng, zoom: Double = position.zoom) {
        move(LeafletCameraPosition(target = target, zoom = zoom))
    }
}

@Composable
fun rememberLeafletCameraPositionState(
    initialPosition: LeafletCameraPosition = LeafletCameraPosition(
        target = LeafletLatLng(latitude = 22.5726, longitude = 88.3639),
        zoom = 12.0
    )
): LeafletCameraPositionState {
    return remember {
        LeafletCameraPositionState(initialPosition = initialPosition)
    }
}
