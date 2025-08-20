package org.example.app.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/**
 * Repository providing access to device music library via MediaStore
 * and in-memory playlist management.
 */
class MusicRepository(private val context: Context) {

    private val playlists = ConcurrentHashMap<Long, Playlist>()
    private val nextPlaylistId = AtomicLong(1)

    // PUBLIC_INTERFACE
    suspend fun loadLibrary(query: String? = null): List<Track> = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION
        )
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0" +
                if (!query.isNullOrBlank()) " AND ${MediaStore.Audio.Media.TITLE} LIKE ?" else ""
        val selectionArgs = if (!query.isNullOrBlank()) arrayOf("%$query%") else null
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val tracks = mutableListOf<Track>()
        context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val albumCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val durationCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val title = cursor.getString(titleCol) ?: "Unknown"
                val artist = cursor.getString(artistCol) ?: "Unknown"
                val albumId = cursor.getLong(albumIdCol)
                val album = cursor.getString(albumCol)
                val duration = cursor.getLong(durationCol)
                val contentUri = ContentUris.withAppendedId(uri, id)
                val artUri = getAlbumArtUri(albumId)
                tracks.add(Track(id, title, artist, album, duration, contentUri, artUri))
            }
        }
        tracks
    }

    private fun getAlbumArtUri(albumId: Long): Uri? {
        return if (albumId > 0) {
            Uri.parse("content://media/external/audio/albumart").buildUpon()
                .appendPath(albumId.toString())
                .build()
        } else null
    }

    // PUBLIC_INTERFACE
    fun getPlaylists(): List<Playlist> = playlists.values.sortedBy { it.name }

    // PUBLIC_INTERFACE
    fun createPlaylist(name: String): Playlist {
        val id = nextPlaylistId.getAndIncrement()
        val pl = Playlist(id, name)
        playlists[id] = pl
        return pl
    }

    // PUBLIC_INTERFACE
    fun addToPlaylist(playlistId: Long, trackId: Long) {
        playlists[playlistId]?.trackIds?.add(trackId)
    }

    // PUBLIC_INTERFACE
    fun removeFromPlaylist(playlistId: Long, trackId: Long) {
        playlists[playlistId]?.trackIds?.remove(trackId)
    }
}
