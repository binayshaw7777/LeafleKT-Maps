(function () {
    const INDIA_BOUNDARY_GEOJSON_PATH = "/assets/leaflet/india-country-outline.min.geojson";

    let boundaryDataPromise = null;
    let boundaryLayer = null;
    let boundMap = null;
    let activeStyleId = "open_street_map";

    function loadBoundaryData() {
        if (boundaryDataPromise !== null) {
            return boundaryDataPromise;
        }

        boundaryDataPromise = fetch(INDIA_BOUNDARY_GEOJSON_PATH)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Failed to load India boundary GeoJSON");
                }

                return response.json();
            });

        return boundaryDataPromise;
    }

    function boundaryPalette(styleId) {
        switch (styleId) {
            case "carto_dark":
                return {
                    borderColor: "#b9c2cc",
                    disputedColor: "#d2a6a6",
                    claimedOpacity: 0.62,
                    disputedOpacity: 0.72
                };
            case "esri_world_imagery":
                return {
                    borderColor: "#d9e2ea",
                    disputedColor: "#e7c9a1",
                    claimedOpacity: 0.68,
                    disputedOpacity: 0.8
                };
            case "open_topo_map":
                return {
                    borderColor: "#8f8776",
                    disputedColor: "#ad9079",
                    claimedOpacity: 0.58,
                    disputedOpacity: 0.7
                };
            case "carto_light":
                return {
                    borderColor: "#8a9099",
                    disputedColor: "#b28b8b",
                    claimedOpacity: 0.56,
                    disputedOpacity: 0.68
                };
            case "open_street_map":
            default:
                return {
                    borderColor: "#8d8f94",
                    disputedColor: "#b38f8f",
                    claimedOpacity: 0.52,
                    disputedOpacity: 0.64
                };
        }
    }

    function boundaryWeight(map) {
        return Math.max(map.getZoom() / 7, 0.85);
    }

    function pathStyle(map, feature) {
        const palette = boundaryPalette(activeStyleId);
        const isDisputed = feature &&
            feature.properties &&
            feature.properties.boundary === "disputed";

        return {
            color: isDisputed ? palette.disputedColor : palette.borderColor,
            weight: isDisputed ? boundaryWeight(map) * 1.15 : boundaryWeight(map),
            opacity: isDisputed ? palette.disputedOpacity : palette.claimedOpacity,
            fill: false,
            interactive: false,
            bubblingMouseEvents: false,
            dashArray: isDisputed ? "6 4" : null,
            lineCap: "round",
            lineJoin: "round"
        };
    }

    function createLayer(map, featureCollection) {
        return L.geoJSON(featureCollection, {
            style: function (feature) {
                return pathStyle(map, feature);
            },
            interactive: false,
            bubblingMouseEvents: false
        });
    }

    function addLayerToMap(map) {
        return loadBoundaryData()
            .then(function (featureCollection) {
                if (boundaryLayer === null) {
                    boundaryLayer = createLayer(map, featureCollection);
                }

                boundaryLayer.setStyle(function (feature) {
                    return pathStyle(map, feature);
                });

                if (!map.hasLayer(boundaryLayer)) {
                    boundaryLayer.addTo(map);
                }
            })
            .catch(function (error) {
                console.warn("[LeafleKT] India boundary overlay failed", error);
            });
    }

    function removeLayerFromMap(map) {
        if (boundaryLayer !== null && map.hasLayer(boundaryLayer)) {
            map.removeLayer(boundaryLayer);
        }
    }

    function bindMap(map) {
        if (boundMap === map) {
            return;
        }

        boundMap = map;
        map.on("zoomend", function () {
            if (boundaryLayer !== null && map.hasLayer(boundaryLayer)) {
                boundaryLayer.setStyle(function (feature) {
                    return pathStyle(map, feature);
                });
            }
        });
    }

    window.LeafleKTIndiaBoundaryOverlay = {
        setVisible: function (map, isVisible) {
            if (!map) {
                return;
            }

            bindMap(map);

            if (isVisible) {
                addLayerToMap(map);
                return;
            }

            removeLayerFromMap(map);
        },
        setMapStyle: function (map, styleId) {
            if (!map) {
                return;
            }

            activeStyleId = styleId || "open_street_map";
            bindMap(map);

            if (boundaryLayer !== null && map.hasLayer(boundaryLayer)) {
                boundaryLayer.setStyle(function (feature) {
                    return pathStyle(map, feature);
                });
            }
        }
    };
})();
