package com.binayshaw7777.leaflekt.library

/**
 * Internal script builder that generates JavaScript commands for the Leaflet runtime.
 */
internal object LeaflektScriptBuilder {
    fun initMapScript(lat: Double, lng: Double, zoom: Double): String {
        return "window.LeaflektBridge.initMap($lat,$lng,$zoom);"
    }

    fun setZoomControlsEnabledScript(isEnabled: Boolean): String {
        return "window.LeaflektBridge.setZoomControlsEnabled($isEnabled);"
    }

    fun setScrollGesturesEnabledScript(isEnabled: Boolean): String {
        return "window.LeaflektBridge.setScrollGesturesEnabled($isEnabled);"
    }

    fun setZoomGesturesEnabledScript(isEnabled: Boolean): String {
        return "window.LeaflektBridge.setZoomGesturesEnabled($isEnabled);"
    }

    fun setMapStyleScript(style: LeaflektMapStyle): String {
        return "window.LeaflektBridge.setMapStyle(${style.toJson()});"
    }

    fun moveCameraScript(lat: Double, lng: Double, zoom: Double): String {
        return "window.LeaflektBridge.moveCamera($lat,$lng,$zoom);"
    }

    fun addMarkersScript(markers: List<LeaflektMarkerInfo>): String {
        val payload = markers.joinToString(prefix = "[", postfix = "]") { it.toJson() }
        return "window.LeaflektBridge.addMarkers($payload);"
    }

    fun updateMarkerScript(marker: LeaflektMarkerInfo): String {
        return "window.LeaflektBridge.updateMarker(${marker.toJson()});"
    }

    fun removeMarkerScript(markerId: String): String {
        return "window.LeaflektBridge.removeMarker('$markerId');"
    }

    fun clearMarkersScript(): String {
        return "window.LeaflektBridge.clearMarkers();"
    }

    private fun LeaflektMarkerInfo.toJson(): String {
        return """
            {
                id: '${id ?: ""}',
                lat: $lat,
                lng: $lng,
                title: '${title?.replace("'", "\\'") ?: ""}',
                snippet: '${snippet?.replace("'", "\\'") ?: ""}',
                visible: $visible,
                alpha: $alpha
            }
        """.trimIndent()
    }

    private fun LeaflektMapStyle.toJson(): String {
        val subdomainsJson = if (subdomains != null) "'$subdomains'" else "null"
        return """
            {
                id: '$id',
                tileUrlTemplate: '$url',
                attributionHtml: "$attribution",
                maxZoom: $maxZoom,
                subdomains: $subdomainsJson
            }
        """.trimIndent()
    }
}
