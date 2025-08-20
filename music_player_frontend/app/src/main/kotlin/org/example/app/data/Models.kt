package org.example.app.data

import android.net.Uri

/**
 * PUBLIC_INTERFACE
 * Data class representing a music track.
 */
data class Track(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String?,
    val durationMs: Long,
    val data: Uri,
    val albumArt: Uri?
)

/**
 * PUBLIC_INTERFACE
 * Data class representing a user playlist.
 */
data class Playlist(
    val id: Long,
    val name: String,
    val trackIds: MutableList<Long> = mutableListOf()
)
