package com.binayshaw7777.leaflekt.library

import org.junit.Assert.assertTrue
import org.junit.Test

class LeaflektScriptBuilderTest {
    @Test
    fun addMarkersScriptContainsAllMarkerFields() {
        val script = LeaflektScriptBuilder.addMarkersScript(
            listOf(Marker(id = "id-1", lat = 10.0, lng = 20.0, title = "Point A"))
        )

        assertTrue(script.contains("window.LeafletBridge.addMarkers("))
        assertTrue(script.contains("\"id\":\"id-1\""))
        assertTrue(script.contains("\"lat\":10"))
        assertTrue(script.contains("\"lng\":20"))
        assertTrue(script.contains("\"title\":\"Point A\""))
    }

    @Test
    fun setMapStyleScriptContainsTileStyleMetadata() {
        val script = LeaflektScriptBuilder.setMapStyleScript(LeaflektMapStyle.CartoDark)

        assertTrue(script.contains("window.LeafletBridge.setMapStyle("))
        assertTrue(script.contains("\"id\":\"carto_dark\""))
        assertTrue(script.contains("dark_all"))
        assertTrue(script.contains("\"maxZoom\":20"))
    }
}
