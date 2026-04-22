package com.binayshaw7777.leaflekt.library

internal object LeaflektMapJson {
    fun encodeString(value: String): String {
        val escaped = value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
        return "\"$escaped\""
    }

    fun encodeNullableString(value: String?): String {
        return value?.let(::encodeString) ?: "null"
    }

    fun encodeLatLng(point: LeaflektLatLng): String {
        return """{"latitude":${point.latitude},"longitude":${point.longitude}}"""
    }

    fun encodeLatLngList(points: List<LeaflektLatLng>): String {
        return points.joinToString(prefix = "[", postfix = "]") { encodeLatLng(it) }
    }

    fun encodeLatLngHoles(holes: List<List<LeaflektLatLng>>): String {
        return holes.joinToString(prefix = "[", postfix = "]") { encodeLatLngList(it) }
    }
}
