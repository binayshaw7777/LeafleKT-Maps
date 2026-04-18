package com.binayshaw7777.leaflekt.library

import org.junit.Assert.assertTrue
import org.junit.Test

class LeafletScriptBuilderTest {
    @Test
    fun addMarkersScriptContainsAllMarkerFields() {
        val script = LeafletScriptBuilder.addMarkersScript(
            listOf(Marker(id = "id-1", lat = 10.0, lng = 20.0, title = "Point A"))
        )

        assertTrue(script.contains("window.LeafletBridge.addMarkers("))
        assertTrue(script.contains("\"id\":\"id-1\""))
        assertTrue(script.contains("\"lat\":10"))
        assertTrue(script.contains("\"lng\":20"))
        assertTrue(script.contains("\"title\":\"Point A\""))
    }
}
