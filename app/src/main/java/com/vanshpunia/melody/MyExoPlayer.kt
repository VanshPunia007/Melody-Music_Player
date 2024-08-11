package com.vanshpunia.melody

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.vanshpunia.melody.models.Song

object MyExoPlayer {
    var exoPlayer: ExoPlayer? = null
    var currentSong: Song? = null

    fun getInstance(): ExoPlayer? {
        return exoPlayer
    }

    fun currentSong():Song?{
        return currentSong
    }

    fun startPlaying(context: Context, song: Song) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }

        if (currentSong?.id != song.id) {
            currentSong = song
            currentSong?.url?.apply {
                val mediaItem = MediaItem.fromUri(this)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()
            }
        } else {
            // If it's the same song, check the playback state
            if (exoPlayer?.isPlaying == false) {
                // If not playing, start playback
                exoPlayer?.play()
            }
        }
    }
}