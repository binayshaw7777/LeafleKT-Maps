package com.binayshaw7777.leaflekt.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlin.math.max
import java.util.UUID

@Composable
@LeaflektMapComposable
fun LeaflektCircle(
    state: LeaflektCircleState = rememberLeaflektCircleState(),
    clickable: Boolean = false,
    fillColor: Color = Color.Transparent,
    strokeColor: Color = Color.Black,
    strokePattern: List<LeaflektStrokePattern>? = null,
    strokeWidth: Float = 10f,
    visible: Boolean = true,
    zIndex: Float = 0f,
    fillOpacity: Float = 0.2f,
    strokeOpacity: Float = 1f,
    selectedStrokeColor: Color = DefaultLeaflektSelectedStrokeColor,
    selectedStrokeWidth: Float = strokeWidth + SelectedLeaflektCircleStrokeWidthBoost,
    selectedFillOpacity: Float = max(fillOpacity, SelectedLeaflektMinimumFillOpacity),
    selectedZIndexBoost: Float = SelectedLeaflektZIndexBoost,
    id: String = remember { UUID.randomUUID().toString() },
    onClick: () -> Unit = {}
) {
    val controller = LocalLeaflektController.current ?: return
    val resolvedStrokeColor = if (state.isSelected) selectedStrokeColor else strokeColor
    val resolvedStrokeWidth = if (state.isSelected) max(selectedStrokeWidth, strokeWidth) else strokeWidth
    val resolvedFillOpacity = if (state.isSelected) max(selectedFillOpacity, fillOpacity) else fillOpacity
    val resolvedZIndex = if (state.isSelected) zIndex + selectedZIndexBoost else zIndex

    DisposableEffect(id) {
        controller.addCircle(
            LeaflektCircleInfo(
                id = id,
                center = state.center,
                clickable = clickable,
                fillColor = fillColor,
                radiusMeters = state.radiusMeters,
                strokeColor = resolvedStrokeColor,
                strokePattern = strokePattern,
                strokeWidth = resolvedStrokeWidth,
                visible = visible,
                zIndex = resolvedZIndex,
                fillOpacity = resolvedFillOpacity,
                strokeOpacity = strokeOpacity
            )
        )
        controller.registerCircleClick(id, onClick)

        onDispose {
            controller.unregisterCircleClick(id)
            controller.removeCircle(id)
        }
    }

    DisposableEffect(id, onClick) {
        controller.registerCircleClick(id, onClick)
        onDispose {
            controller.unregisterCircleClick(id)
        }
    }

    LaunchedEffect(
        state.center,
        state.radiusMeters,
        state.isSelected,
        clickable,
        fillColor,
        strokeColor,
        strokePattern,
        strokeWidth,
        visible,
        zIndex,
        fillOpacity,
        strokeOpacity,
        selectedStrokeColor,
        selectedStrokeWidth,
        selectedFillOpacity,
        selectedZIndexBoost
    ) {
        controller.updateCircle(
            LeaflektCircleInfo(
                id = id,
                center = state.center,
                clickable = clickable,
                fillColor = fillColor,
                radiusMeters = state.radiusMeters,
                strokeColor = resolvedStrokeColor,
                strokePattern = strokePattern,
                strokeWidth = resolvedStrokeWidth,
                visible = visible,
                zIndex = resolvedZIndex,
                fillOpacity = resolvedFillOpacity,
                strokeOpacity = strokeOpacity
            )
        )
    }
}

@Composable
@LeaflektMapComposable
fun LeaflektCircle(
    center: LeaflektLatLng,
    clickable: Boolean = false,
    fillColor: Color = Color.Transparent,
    radiusMeters: Double = 10.0,
    strokeColor: Color = Color.Black,
    strokePattern: List<LeaflektStrokePattern>? = null,
    strokeWidth: Float = 10f,
    visible: Boolean = true,
    zIndex: Float = 0f,
    fillOpacity: Float = 0.2f,
    strokeOpacity: Float = 1f,
    selected: Boolean = false,
    selectedStrokeColor: Color = DefaultLeaflektSelectedStrokeColor,
    selectedStrokeWidth: Float = strokeWidth + SelectedLeaflektCircleStrokeWidthBoost,
    selectedFillOpacity: Float = max(fillOpacity, SelectedLeaflektMinimumFillOpacity),
    selectedZIndexBoost: Float = SelectedLeaflektZIndexBoost,
    id: String = remember { UUID.randomUUID().toString() },
    onClick: () -> Unit = {}
) {
    val controller = LocalLeaflektController.current ?: return
    val resolvedStrokeColor = if (selected) selectedStrokeColor else strokeColor
    val resolvedStrokeWidth = if (selected) max(selectedStrokeWidth, strokeWidth) else strokeWidth
    val resolvedFillOpacity = if (selected) max(selectedFillOpacity, fillOpacity) else fillOpacity
    val resolvedZIndex = if (selected) zIndex + selectedZIndexBoost else zIndex

    DisposableEffect(id) {
        controller.addCircle(
            LeaflektCircleInfo(
                id = id,
                center = center,
                clickable = clickable,
                fillColor = fillColor,
                radiusMeters = radiusMeters,
                strokeColor = resolvedStrokeColor,
                strokePattern = strokePattern,
                strokeWidth = resolvedStrokeWidth,
                visible = visible,
                zIndex = resolvedZIndex,
                fillOpacity = resolvedFillOpacity,
                strokeOpacity = strokeOpacity
            )
        )
        controller.registerCircleClick(id, onClick)

        onDispose {
            controller.unregisterCircleClick(id)
            controller.removeCircle(id)
        }
    }

    DisposableEffect(id, onClick) {
        controller.registerCircleClick(id, onClick)
        onDispose {
            controller.unregisterCircleClick(id)
        }
    }

    LaunchedEffect(
        center,
        radiusMeters,
        selected,
        clickable,
        fillColor,
        strokeColor,
        strokePattern,
        strokeWidth,
        visible,
        zIndex,
        fillOpacity,
        strokeOpacity,
        selectedStrokeColor,
        selectedStrokeWidth,
        selectedFillOpacity,
        selectedZIndexBoost
    ) {
        controller.updateCircle(
            LeaflektCircleInfo(
                id = id,
                center = center,
                clickable = clickable,
                fillColor = fillColor,
                radiusMeters = radiusMeters,
                strokeColor = resolvedStrokeColor,
                strokePattern = strokePattern,
                strokeWidth = resolvedStrokeWidth,
                visible = visible,
                zIndex = resolvedZIndex,
                fillOpacity = resolvedFillOpacity,
                strokeOpacity = strokeOpacity
            )
        )
    }
}

private const val SelectedLeaflektCircleStrokeWidthBoost = 2f
