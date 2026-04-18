package com.binayshaw7777.leaflekt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binayshaw7777.leaflekt.library.LeafletController
import com.binayshaw7777.leaflekt.library.LeafletMap
import com.binayshaw7777.leaflekt.library.Marker
import com.binayshaw7777.leaflekt.ui.theme.LeafleKTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeafleKTTheme {
                LeafletDemoScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun LeafletDemoScreen(modifier: Modifier = Modifier) {
    var controller by remember { mutableStateOf<LeafletController?>(null) }
    var markerSequence by rememberSaveable { mutableIntStateOf(0) }
    var selectedZoom by rememberSaveable { mutableFloatStateOf(12f) }
    var activeCenterLat by rememberSaveable { mutableDoubleStateOf(22.5726) }
    var activeCenterLng by rememberSaveable { mutableDoubleStateOf(88.3639) }
    var lastTap by rememberSaveable { mutableStateOf("Tap on map to capture coordinates") }
    var lastMarkerId by rememberSaveable { mutableStateOf("No marker clicked yet") }

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(text = "LeafleKT Demo", style = MaterialTheme.typography.titleLarge)
            Text(text = lastTap, style = MaterialTheme.typography.bodySmall)
            Text(text = "Marker click: $lastMarkerId", style = MaterialTheme.typography.bodySmall)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    activeCenterLat = 22.5726
                    activeCenterLng = 88.3639
                    controller?.setCenter(activeCenterLat, activeCenterLng, selectedZoom.toDouble())
                }
            ) {
                Text("Kolkata")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    activeCenterLat = 12.9716
                    activeCenterLng = 77.5946
                    controller?.setCenter(activeCenterLat, activeCenterLng, selectedZoom.toDouble())
                }
            ) {
                Text("Bengaluru")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = { controller?.clearMarkers() }
            ) {
                Text("Clear")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Zoom: ${selectedZoom.toInt()}", modifier = Modifier.size(72.dp))
            Slider(
                modifier = Modifier.weight(1f),
                value = selectedZoom,
                onValueChange = { selectedZoom = it },
                valueRange = 3f..18f,
                onValueChangeFinished = {
                    controller?.setCenter(activeCenterLat, activeCenterLng, selectedZoom.toDouble())
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    markerSequence += 1
                    controller?.addMarker(
                        Marker(
                            id = "single-$markerSequence",
                            lat = activeCenterLat,
                            lng = activeCenterLng,
                            title = "Single marker #$markerSequence"
                        )
                    )
                }
            ) {
                Text("Add Marker")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    markerSequence += 1
                    val base = markerSequence * 10
                    controller?.addMarkers(
                        listOf(
                            Marker(
                                id = "cluster-${base + 1}",
                                lat = activeCenterLat + 0.01,
                                lng = activeCenterLng + 0.01,
                                title = "Cluster A"
                            ),
                            Marker(
                                id = "cluster-${base + 2}",
                                lat = activeCenterLat + 0.02,
                                lng = activeCenterLng - 0.01,
                                title = "Cluster B"
                            ),
                            Marker(
                                id = "cluster-${base + 3}",
                                lat = activeCenterLat - 0.02,
                                lng = activeCenterLng + 0.01,
                                title = "Cluster C"
                            )
                        )
                    )
                }
            ) {
                Text("Add 3")
            }
        }

        LeafletMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            initialCenterLat = activeCenterLat,
            initialCenterLng = activeCenterLng,
            initialZoom = selectedZoom.toDouble(),
            onReady = { leafletController ->
                Log.d("LeafleKT.Demo", "onReady")
                controller = leafletController
                leafletController.setCenter(activeCenterLat, activeCenterLng, selectedZoom.toDouble())
            },
            onMapClick = { lat, lng ->
                Log.d("LeafleKT.Demo", "onMapClick lat=$lat lng=$lng")
                activeCenterLat = lat
                activeCenterLng = lng
                lastTap = "Tap: %.5f, %.5f".format(lat, lng)
            },
            onMarkerClick = { markerId ->
                Log.d("LeafleKT.Demo", "onMarkerClick id=$markerId")
                lastMarkerId = markerId
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LeafletDemoPreview() {
    LeafleKTTheme {
        LeafletDemoScreen()
    }
}
