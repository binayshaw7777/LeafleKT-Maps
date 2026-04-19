# LeafleKT 🌿

<img src="https://github.com/user-attachments/assets/d9544533-7c4a-4653-9364-cfd631314368"
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

## ⚠️ Legal Disclaimer (FYI)

- **Not an Official Product:** LeafleKT is an independent open-source project. It is **not** affiliated with, authorized, maintained, sponsored, or endorsed by Google LLC, the Leaflet.js team, or the OpenStreetMap Foundation.
- **API Design:** The API pattern (e.g., `CameraPositionState`) is modeled after Google Maps Compose to provide a familiar experience for Android developers. All implementation code is original to this project and does not use proprietary Google source code.
- **Geopolitical Data:** The India boundary overlay is provided based on the author's recognition and representation of national borders. It is included for general visualization purposes. In future releases, I intend to offer developers the flexibility to customize or provide their own boundary data to meet their specific regional requirements. Developers are responsible for ensuring compliance with local laws regarding map displays in their specific regions.

## 📄 License
LeafleKT is licensed under the **Apache License 2.0**. See the [LICENSE](LICENSE) file for details.

---

## 📈 Star History

[![Star History Chart](https://api.star-history.com/svg?repos=binayshaw7777/LeafleKT-Maps&type=Date)](https://star-history.com/#binayshaw7777/LeafleKT-Maps)

<p align="center">
  Made with ❤️ for the Android Community
</p>
