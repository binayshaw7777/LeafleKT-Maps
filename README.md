# LeafleKT 🌿

<img src="https://github.com/user-attachments/assets/4f7a8756-b98d-4eb4-8948-53b8b2f40cdd"
     style="max-width:100%; height:auto;" />

[![JitPack](https://jitpack.io/v/binayshaw7777/LeafleKT-Maps.svg)](https://jitpack.io/#binayshaw7777/LeafleKT-Maps)
[![Android API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
![Build Status](https://github.com/binayshaw7777/LeafleKT-Maps/actions/workflows/release-master.yml/badge.svg)

**LeafleKT** is a high-performance, Compose-first Android wrapper around [Leaflet.js](https://leafletjs.com/). It brings the power of the web's most popular mapping engine into native Jetpack Compose, offering a stable state-driven Kotlin API while keeping the runtime entirely self-contained.

---

## 🚀 Key Features

- 🎨 **Compose Native:** Pure `@Composable` API modeled after Google Maps Compose.
- 📦 **Zero-Config Runtime:** Bundles Leaflet JS/CSS locally—no external CDNs required.
- ⚡ **Optimized Assets:** Built-in India country boundary overlay (313 KB optimized linework).
- 📍 **Marker Management:** Effortless single and batch marker placement with click callbacks.
- 🛠️ **State Driven:** Fully reactive camera, properties, and UI settings.
- 🌐 **Style Switching:** Hot-swap between OSM, Carto Light/Dark, Topo, and Satellite themes.
- 🛡️ **Safe Navigation:** Attribution links open safely in the system browser.

---

## 📦 Installation

To get LeafleKT into your build:

**Step 1: Add the JitPack repository to your build file**

Add it in your `settings.gradle.kts` at the end of repositories:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**Step 2: Add the dependency**

```kotlin
dependencies {
    implementation("com.github.binayshaw7777:LeafleKT-Maps:0.4.0")
}
```

---

## 🛠 Quick Start

```kotlin
@Composable
fun MyMap() {
    val cameraPositionState = rememberLeaflektCameraPositionState(
        initialPosition = LeaflektCameraPosition(
            target = LeaflektLatLng(22.5726, 88.3639),
            zoom = 12.0
        )
    )

    LeaflektMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = LeaflektMapProperties(
            mapStyle = LeaflektMapStyle.OpenStreetMap
        ),
        onMapClick = { latLng ->
            Log.d("Map", "Tapped at: $latLng")
        }
    )
}
```

---

## 📖 Documentation

### **The Composable Surface**
The `LeaflektMap` is the primary entry point. It supports standard modifiers and content overlaying.
```kotlin
LeaflektMap(
    modifier = Modifier.fillMaxSize(),
    uiSettings = LeaflektMapUiSettings(isZoomControlEnabled = true),
    onMapLoaded = { /* handle ready */ }
) {
    // Standard Compose content can be placed on top of the map
    FloatingActionButton(onClick = { /* ... */ }) { Text("+") }
}
```

### **Built-in Styles**
LeafleKT comes with professionally tuned styles out of the box:
- `LeaflektMapStyle.OpenStreetMap`
- `LeaflektMapStyle.CartoLight`
- `LeaflektMapStyle.CartoDark`
- `LeaflektMapStyle.OpenTopoMap`
- `LeaflektMapStyle.EsriWorldImagery` (Satellite)

### **India Boundary Overlay**
The library includes a pre-configured India border overlay that reacts to the active map style. It uses an optimized GeoJSON to ensure smooth performance without bloating your APK.

---

## ⚖️ Acknowledgments & Licensing

- **Leaflet.js:** This library is a wrapper around [Leaflet.js](https://leafletjs.com/), which is licensed under the [BSD 2-Clause License](https://github.com/Leaflet/Leaflet/blob/master/LICENSE).
- **Map Data:** This library does not provide map tiles. Users are responsible for complying with the attribution requirements of their chosen tile provider (e.g., [OpenStreetMap](https://www.openstreetmap.org/copyright)).
- **India Boundaries:** Boundary data is derived from OpenStreetMap contributors and optimized for mobile performance.

## 📄 License
LeafleKT is licensed under the **Apache License 2.0**. See the [LICENSE](LICENSE) file for details.

---

## 📈 Star History

[![Star History Chart](https://api.star-history.com/svg?repos=binayshaw7777/LeafleKT-Maps&type=Date)](https://star-history.com/#binayshaw7777/LeafleKT-Maps)

<p align="center">
  Made with ❤️ for the Android Community
</p>
