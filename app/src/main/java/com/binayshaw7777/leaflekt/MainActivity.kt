package com.binayshaw7777.leaflekt

import android.os.Bundle
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.leaflekt.library.LeaflektCameraPosition
import com.binayshaw7777.leaflekt.library.LeaflektCameraPositionState
import com.binayshaw7777.leaflekt.library.LeaflektController
import com.binayshaw7777.leaflekt.library.LeaflektLatLng
import com.binayshaw7777.leaflekt.library.LeaflektMap
import com.binayshaw7777.leaflekt.library.LeaflektMapProperties
import com.binayshaw7777.leaflekt.library.LeaflektMapStyle
import com.binayshaw7777.leaflekt.library.LeaflektMapUiSettings
import com.binayshaw7777.leaflekt.library.LeaflektMarker
import com.binayshaw7777.leaflekt.library.LeaflektMarkerInfo
import com.binayshaw7777.leaflekt.library.rememberLeaflektCameraPositionState
import com.binayshaw7777.leaflekt.ui.theme.LeafleKTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeafleKTTheme {
                LeaflektDemoScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LeaflektDemoScreen(modifier: Modifier = Modifier) {
    var controller by remember { mutableStateOf<LeaflektController?>(null) }
    var markerSequence by rememberSaveable { mutableIntStateOf(0) }
    var selectedZoom by rememberSaveable { mutableFloatStateOf(12f) }
    var activeMarkerLat by rememberSaveable { mutableDoubleStateOf(22.5726) }
    var activeMarkerLng by rememberSaveable { mutableDoubleStateOf(88.3639) }
    var selectedMapStyle by rememberSaveable { mutableStateOf(LeaflektMapStyle.OpenStreetMap) }
    var isStyleMenuExpanded by remember { mutableStateOf(false) }
    var lastTap by rememberSaveable { mutableStateOf("Tap on map to capture coordinates") }
    var lastMarkerId by rememberSaveable { mutableStateOf("No marker clicked yet") }
    
    // Declarative markers list
    val declarativeMarkers = remember { mutableStateListOf<LeaflektMarkerInfo>() }

    val cameraPositionState = rememberLeaflektCameraPositionState {
        position = LeaflektCameraPosition(
            target = LeaflektLatLng(latitude = 22.5726, longitude = 88.3639),
            zoom = 12.0
        )
    }
    val mapProperties = LeaflektMapProperties(mapStyle = selectedMapStyle)
    val mapUiSettings = LeaflektMapUiSettings(zoomControlsEnabled = false)

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(text = "LeafleKT Demo", style = MaterialTheme.typography.titleLarge)
            Text(text = lastTap, style = MaterialTheme.typography.bodySmall)
            Text(text = "Marker click: $lastMarkerId", style = MaterialTheme.typography.bodySmall)
        }

        ExposedDropdownMenuBox(
            expanded = isStyleMenuExpanded,
            onExpandedChange = { isStyleMenuExpanded = it }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth(),
                value = selectedMapStyle.displayName(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Map style") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isStyleMenuExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = isStyleMenuExpanded,
                onDismissRequest = { isStyleMenuExpanded = false }
            ) {
                LeaflektMapStyle.entries.forEach { mapStyle ->
                    DropdownMenuItem(
                        text = { Text(mapStyle.displayName()) },
                        onClick = {
                            selectedMapStyle = mapStyle
                            isStyleMenuExpanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    cameraPositionState.move(
                        target = LeaflektLatLng(latitude = 22.5726, longitude = 88.3639),
                        zoom = selectedZoom.toDouble()
                    )
                }
            ) {
                Text("Kolkata", fontSize = 14.sp)
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    cameraPositionState.move(
                        target = LeaflektLatLng(latitude = 12.9716, longitude = 77.5946),
                        zoom = selectedZoom.toDouble()
                    )
                }
            ) {
                Text(text = "Bengaluru", fontSize = 14.sp)
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = { declarativeMarkers.clear() }
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
                    cameraPositionState.move(
                        target = cameraPositionState.position.target,
                        zoom = selectedZoom.toDouble()
                    )
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
                    declarativeMarkers.add(
                        LeaflektMarkerInfo(
                            id = "marker-$markerSequence",
                            lat = activeMarkerLat,
                            lng = activeMarkerLng,
                            title = "Marker #$markerSequence"
                        )
                    )
                }
            ) {
                Text("Add Marker")
            }
        }

        LeaflektMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cameraPositionState = cameraPositionState,
            contentDescription = "LeafleKT demo map app",
            properties = mapProperties,
            uiSettings = mapUiSettings,
            onReady = { leaflektController ->
                controller = leaflektController
            },
            onMapClick = { point ->
                activeMarkerLat = point.latitude
                activeMarkerLng = point.longitude
                lastTap = "Tap: %.5f, %.5f".format(point.latitude, point.longitude)
            },
            onMarkerClick = { markerId ->
                lastMarkerId = markerId
            }
        ) {
            // Declarative Markers!
            declarativeMarkers.forEach { marker ->
                LeaflektMarker(
                    position = LeaflektLatLng(marker.lat, marker.lng),
                    title = marker.title,
                    id = marker.id ?: ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LeaflektDemoPreview() {
    LeafleKTTheme {
        LeaflektDemoScreen()
    }
}

private fun LeaflektMapStyle.displayName(): String {
    return when (this) {
        LeaflektMapStyle.OpenStreetMap -> "OpenStreetMap"
        LeaflektMapStyle.CartoLight -> "CARTO Light"
        LeaflektMapStyle.CartoDark -> "CARTO Dark"
        LeaflektMapStyle.OpenTopoMap -> "OpenTopoMap"
        LeaflektMapStyle.EsriWorldImagery -> "Esri World Imagery"
    }
}
