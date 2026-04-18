package com.binayshaw7777.leaflekt.library

internal object LeafletScriptBuilder {
    fun initMapScript(lat: Double, lng: Double, zoom: Double): String {
        return "window.LeafletBridge.initMap($lat,$lng,$zoom);"
    }

    fun moveCameraScript(lat: Double, lng: Double, zoom: Double): String {
        return "window.LeafletBridge.moveCamera($lat,$lng,$zoom);"
    }

    fun addMarkersScript(markers: List<Marker>): String {
        val payload = markers.joinToString(prefix = "[", postfix = "]") { marker ->
            marker.toJson()
        }
        return "window.LeafletBridge.addMarkers($payload);"
    }

    fun clearMarkersScript(): String {
        return "window.LeafletBridge.clearMarkers();"
    }

    private fun Marker.toJson(): String {
        val encodedId = id.encodeJsonString()
        val encodedTitle = title?.encodeJsonString() ?: "null"
        return """{"id":$encodedId,"lat":$lat,"lng":$lng,"title":$encodedTitle}"""
    }

    private fun String.encodeJsonString(): String {
        val escaped = this
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
        return "\"$escaped\""
    }
}
