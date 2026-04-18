package com.binayshaw7777.leaflekt.library

data class LeafletMapProperties(
    val mapStyle: LeafletMapStyle = LeafletMapStyle.OpenStreetMap
)

val DefaultLeafletMapProperties = LeafletMapProperties()
