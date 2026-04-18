package com.binayshaw7777.leaflekt.library

data class LeafletMapProperties(
    val mapStyle: LeafletMapStyle = LeafletMapStyle.OpenStreetMap,
    val isIndiaBoundaryOverlayVisible: Boolean = true
)

val DefaultLeafletMapProperties = LeafletMapProperties()
