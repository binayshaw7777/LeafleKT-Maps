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

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [LeaflektScriptBuilder].
 */
class LeaflektScriptBuilderTest {

    @Test
    fun testAddMarkersScriptGeneration() {
        val markers = listOf(
            LeaflektMarkerInfo(id = "1", lat = 22.5726, lng = 88.3639, title = "Kolkata"),
            LeaflektMarkerInfo(id = "2", lat = 12.9716, lng = 77.5946, title = "Bengaluru")
        )

        val script = LeaflektScriptBuilder.addMarkersScript(markers)

        assertTrue(script.contains("window.LeaflektBridge.addMarkers"))
        assertTrue(script.contains("lat: 22.5726"))
        assertTrue(script.contains("lng: 88.3639"))
        assertTrue(script.contains("title: 'Kolkata'"))
        assertTrue(script.contains("id: '1'"))
    }

    @Test
    fun testMoveCameraScriptGeneration() {
        val script = LeaflektScriptBuilder.moveCameraScript(22.5, 88.3, 10.0)
        assertTrue(script.contains("window.LeaflektBridge.moveCamera(22.5,88.3,10.0)"))
    }
}
