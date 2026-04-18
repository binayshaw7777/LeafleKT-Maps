# LeafleKT

LeafleKT is a Compose-first Android wrapper around Leaflet.js. It renders a real Leaflet map inside `WebView`, exposes a stable state-driven Kotlin API, and keeps the runtime self-contained by bundling Leaflet assets locally.

## What it gives you

- Compose API for embedding a Leaflet map with `LeafletMap`
- State-driven API modeled after Google Maps Compose patterns
- Controller API for markers and imperative escape hatches
- India boundary overlay rendered from bundled GeoJSON linework
- JavaScript bridge for map tap and marker tap callbacks
- Local Leaflet runtime assets bundled inside the library module
- Demo app with runtime controls for zoom, markers, and map style switching

## Modules

- `:leaflekt`
  - reusable Android library module
  - contains WebView host, JS bridge, controller, bundled Leaflet assets
- `:app`
  - demo app
  - shows usage patterns and supported SDK features

## Status

Current scope is intentionally small and stable:

- map render in Compose
- camera move
- single marker add
- batch marker add
- clear markers
- map click callback
- marker click callback
- built-in tile style switching
- always-on India boundary overlay

## Install

Local project usage:

```kotlin
dependencies {
    implementation(project(":leaflekt"))
}
```

Planned JitPack usage:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.<your-user-or-org>:LeafleKT:<version>")
}
```

## Quick start

```kotlin
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.binayshaw7777.leaflekt.library.LeafletCameraPosition
import com.binayshaw7777.leaflekt.library.LeafletLatLng
import com.binayshaw7777.leaflekt.library.LeafletMap
import com.binayshaw7777.leaflekt.library.LeafletMapProperties
import com.binayshaw7777.leaflekt.library.LeafletMapStyle
import com.binayshaw7777.leaflekt.library.rememberLeafletCameraPositionState

@Composable
fun MapScreen() {
    val cameraPositionState = rememberLeafletCameraPositionState(
        initialPosition = LeafletCameraPosition(
            target = LeafletLatLng(latitude = 22.5726, longitude = 88.3639),
            zoom = 12.0
        )
    )

    LeafletMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = LeafletMapProperties(
            mapStyle = LeafletMapStyle.OpenStreetMap
        ),
        onMapClick = { point ->
            // handle map tap
        },
        onMapLoaded = {
            // map ready
        }
    )
}
```

## Public API

Main composable:

```kotlin
LeafletMap(
    modifier = Modifier,
    cameraPositionState = rememberLeafletCameraPositionState(),
    contentDescription = "Map",
    properties = DefaultLeafletMapProperties,
    uiSettings = DefaultLeafletMapUiSettings,
    onReady = { controller -> },
    onMapLoaded = { },
    onMapClick = { point -> },
    onMarkerClick = { markerId -> },
    content = { },
)
```

Overlay content:

```kotlin
LeafletMap(
    modifier = Modifier.fillMaxSize(),
    content = {
        FloatingActionButton(
            onClick = { /* overlay action */ }
        ) {
            Text("+")
        }
    }
)
```

Camera state:

```kotlin
val cameraPositionState = rememberLeafletCameraPositionState(
    initialPosition = LeafletCameraPosition(
        target = LeafletLatLng(latitude = 22.5726, longitude = 88.3639),
        zoom = 12.0
    )
)

cameraPositionState.move(
    target = LeafletLatLng(latitude = 12.9716, longitude = 77.5946),
    zoom = 10.0
)
```

Map properties and UI settings:

```kotlin
LeafletMapProperties(
    mapStyle = LeafletMapStyle.CartoDark
)

LeafletMapUiSettings(
    isZoomControlEnabled = true
)
```

Controller methods:

```kotlin
controller.setCenter(lat, lng, zoom)
controller.setZoomControlsEnabled(true)
controller.setMapStyle(LeafletMapStyle.CartoDark)
controller.addMarker(marker)
controller.addMarkers(markers)
controller.clearMarkers()
```

Marker model:

```kotlin
Marker(
    id = "id",
    lat = 22.5726,
    lng = 88.3639,
    title = "Optional title"
)
```

## Built-in map styles

- `LeafletMapStyle.OpenStreetMap`
- `LeafletMapStyle.CartoLight`
- `LeafletMapStyle.CartoDark`
- `LeafletMapStyle.OpenTopoMap`
- `LeafletMapStyle.EsriWorldImagery`

Each style keeps its own tile URL template, attribution text, and max zoom in the SDK.

## India boundary overlay

The SDK includes an always-on India boundary overlay backed by:

- `leaflekt/src/main/assets/leaflet/india-boundaries.js`
- `leaflekt/src/main/assets/leaflet/india-country-outline.min.geojson`

It is wired through the public Kotlin API:

```kotlin
LeafletMap(
    properties = LeafletMapProperties(
        mapStyle = LeafletMapStyle.CartoDark
    )
)
```

Notes:

- the overlay is always enabled by the SDK
- the GeoJSON is kept as a dedicated runtime asset, not inlined in `map.html`
- the overlay data is fetched once, cached, and reused
- the border palette reacts to the active map style so it stays visible on light, dark, topo, and imagery themes

## API direction

LeafleKT now follows the same high-level usage model as Google Maps Compose:

- camera is modeled as a remembered state object
- map-wide options are grouped into properties and UI settings
- callbacks are declarative and recomposition-safe
- imperative controller calls remain available for markers and escape-hatch actions

## Acknowledgments & Licensing

- **Leaflet.js:** This library is a wrapper around [Leaflet.js](https://leafletjs.com/), which is licensed under the [BSD 2-Clause License](https://github.com/Leaflet/Leaflet/blob/master/LICENSE).
- **Map Data:** This library does not provide map tiles. You are responsible for complying with the terms of service and attribution requirements of your chosen tile provider (e.g., [OpenStreetMap](https://www.openstreetmap.org/copyright)).
- **India Boundaries:** The optional India boundary overlay uses data derived from OpenStreetMap contributors.

## License

LeafleKT is licensed under the [Apache License 2.0](LICENSE).

## How it works

Runtime flow:

1. Compose renders `LeafletMap`
2. `LeafletWebView` hosts a `WebView`
3. Local HTML from `leaflekt/src/main/assets/map.html` boots Leaflet
4. Kotlin controller pushes commands through `evaluateJavascript`
5. Leaflet events call back into Kotlin through `@JavascriptInterface`

The library uses bundled local Leaflet JS/CSS/images. Only map tiles come from the selected tile provider.

## Requirements

- Android `minSdk 21`
- internet access for remote tile providers
- visible attribution must remain enabled for the selected tile source

`AndroidManifest.xml` in the host app must include:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Feature checklist

Supported now:

- [x] Compose map host
- [x] Leaflet runtime bundled locally
- [x] Camera control
- [x] Single marker add
- [x] Batch marker add
- [x] Marker clear
- [x] Map click callback
- [x] Marker click callback
- [x] Built-in tile style switching
- [x] Always-on India boundary overlay
- [x] Camera state API
- [x] Map properties API
- [x] UI settings API
- [x] Overlay content slot
- [x] Demo app with runtime controls

Leaflet capabilities planned for this wrapper:

- [ ] polylines
- [ ] polygons
- [ ] circles
- [ ] GeoJSON layers
- [ ] custom marker icons
- [ ] popups and tooltips
- [ ] layer controls
- [ ] clustering
- [ ] custom tile providers
- [ ] event expansion
- [ ] performance tuning for large marker sets

Leaflet itself can support much more than the current wrapper surface. This repo should document only what the Kotlin API exposes today, and keep future items explicit as planned work.

## Local development

Build library and demo app:

```bash
./gradlew :leaflekt:assembleDebug :app:assembleDebug
```

Run unit tests:

```bash
./gradlew :leaflekt:testDebugUnitTest
```

Gradle build caching is enabled in `gradle.properties`.

## Project structure

```text
leaflekt/
  src/main/assets/map.html
  src/main/assets/leaflet/
  src/main/java/.../library/
app/
  src/main/java/.../MainActivity.kt
```

## Attribution

Leaflet and tile-provider attribution is part of the product contract. Do not remove or hide attribution from the active tile layer.
