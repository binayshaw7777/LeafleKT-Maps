# LeafleKT

LeafleKT is a Compose-first Android wrapper around Leaflet.js using a WebView + JavaScript bridge.

## Modules

- `:leaflekt` - reusable Android library module
- `:app` - demo app with runtime controls for camera, zoom, and marker operations

## Install (JitPack-ready template)

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.<your-user-or-org>:LeafleKT:v0.1.0")
}
```

Until publishing is configured, you can consume it locally via project dependency:

```kotlin
implementation(project(":leaflekt"))
```

## Usage

```kotlin
var controller: LeafletController? = null

LeafletMap(
    modifier = Modifier.fillMaxSize(),
    initialCenterLat = 22.5726,
    initialCenterLng = 88.3639,
    initialZoom = 12.0,
    onReady = { controller = it },
    onMapClick = { lat, lng ->
        // observe tap coordinates
    },
    onMarkerClick = { id ->
        // observe clicked marker id
    }
)

controller?.addMarker(
    Marker(
        id = "marker-1",
        lat = 22.5726,
        lng = 88.3639,
        title = "Kolkata"
    )
)
```

## Leaflet Bridge API

The HTML runtime exposes:

- `window.LeafletBridge.initMap(lat, lng, zoom)`
- `window.LeafletBridge.addMarker(lat, lng, title)`
- `window.LeafletBridge.addMarkers(list)`
- `window.LeafletBridge.moveCamera(lat, lng, zoom)`
- `window.LeafletBridge.clearMarkers()`

## Attribution

Leaflet and OpenStreetMap attribution is preserved in the map tile layer and must remain visible.
