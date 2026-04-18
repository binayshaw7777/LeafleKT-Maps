# LeafleKT (Leaflet + Compose) - Execution Checklist

## Architecture (Locked)
- [x] Jetpack Compose UI + Android WebView + JavaScript Bridge + Leaflet.js stack implemented
- [x] Data flow wired: Compose -> Controller -> WebView -> JS Bridge -> Leaflet
- [x] Reverse callbacks wired: Leaflet -> JS Bridge -> Kotlin callbacks -> Compose state

## Day 1 MVP Phases

### Phase 0 - Repo Setup
- [x] Repo name is `LeafleKT`
- [ ] Public GitHub repo
- [ ] Default branch `main`
- [x] Add `README.md`

### Phase 1 - Android Library Setup
- [x] New module created: `:leaflekt` (Android Library)
- [x] minSdk >= 21
- [x] compileSdk latest in project
- [x] Compose enabled
- [x] Compose UI dependency added
- [x] Lifecycle runtime dependency added

### Phase 2 - WebView Base
- [x] `LeafletWebView.kt` created
- [x] AndroidView wrapper added
- [x] WebView settings: JavaScript enabled
- [x] WebView settings: DOM storage enabled
- [x] WebView settings: file access enabled
- [x] WebView settings: loadWithOverviewMode enabled
- [x] Web contents debugging enabled

### Phase 3 - HTML Engine
- [x] `leaflekt/src/main/assets/map.html` created
- [x] Leaflet CSS bundled locally
- [x] Leaflet JS bundled locally
- [x] Fullscreen map div + no margins
- [x] JS map initialization
- [x] Default center and zoom
- [x] OpenStreetMap tile layer with visible attribution

### Phase 4 - JS API
- [x] `window.LeafletBridge.initMap(lat, lng, zoom)`
- [x] `window.LeafletBridge.addMarker(lat, lng, title)`
- [x] `window.LeafletBridge.addMarkers(list)`
- [x] `window.LeafletBridge.moveCamera(lat, lng, zoom)`
- [x] `window.LeafletBridge.clearMarkers()`
- [x] Internal map reference maintained
- [x] Internal marker collection maintained

### Phase 5 - Kotlin Bridge
- [x] `LeafletJsBridge.kt` created
- [x] `@JavascriptInterface` bridge methods added
- [x] `onMapReady()` implemented
- [x] `onMarkerClick(id)` implemented
- [x] `onMapClick(lat, lng)` implemented

### Phase 6 - Kotlin to JS Executor
- [x] `JsExecutor.kt` created
- [x] `runJS(script: String)` implemented
- [x] safe execution wrapper with error handling

### Phase 7 - Controller Layer
- [x] `LeafletController.kt` created
- [x] Holds WebView reference
- [x] Exposes API methods
- [x] `setCenter()` implemented
- [x] `addMarker()` implemented
- [x] `addMarkers()` implemented
- [x] `clearMarkers()` implemented

### Phase 8 - Compose API
- [x] `LeafletMap.kt` created
- [x] Composable API implemented
- [x] Controller provided through `onReady`
- [x] Map click callback exposed
- [x] Marker click callback exposed

### Phase 9 - Data Models
- [x] `Marker.kt` created
- [x] Marker model fields aligned with plan

### Phase 10 - Testing
- [x] Map loads (manual runtime)
- [x] Marker added (manual runtime)
- [x] Multiple markers (manual runtime)
- [x] Camera moves (manual runtime)
- [x] No crashes (manual runtime)
- [x] Unit test added for marker-to-JS payload generation
- [x] `:leaflekt:testDebugUnitTest` passes
- [x] `:app:assembleDebug` passes

### Phase 11 - Documentation
- [x] README added
- [x] Usage example added
- [x] Attribution note added
- [x] Public API KDocs added

### Phase 12 - Deployment
- [ ] Push code
- [ ] Create tag `v0.1.0`
- [ ] Verify JitPack build

### Phase 13 - Launch
- [ ] Record demo
- [ ] LinkedIn post

## Week Plan

### Week 1
- [x] Zoom controls (demo app)
- [x] Marker click callbacks
- [x] Built-in tile style switching
- [x] Optional India boundary overlay toggle
- [ ] Polylines
- [ ] Polygons
- [ ] Circles

### Week 2
- [ ] Clustering plugin
- [ ] GeoJSON
- [ ] Tile switching
- [ ] Custom styles

### Week 3
- [ ] Custom markers
- [ ] Tooltips
- [ ] Event system
- [ ] Performance optimizations

## Success Criteria
- [x] Map loads in Compose
- [x] Marker API implemented
- [ ] JitPack dependency verified
- [x] Dev integration path documented (<5 min local module integration)

## Tracking Notes
- Keep `:leaflekt` reusable and independent from `:app`
- `:app` remains a demo/sample surface with runtime tuning controls
- Last updated: 2026-04-18
