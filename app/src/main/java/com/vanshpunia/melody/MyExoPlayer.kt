package com.vanshpunia.melody

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.vanshpunia.melody.models.Song
import com.vanshpunia.melody.utils.SONGS

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
            updateView()
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

    fun updateView(){
        currentSong()?.id?.let {id->

            Firebase.firestore.collection(SONGS)
                .document(id)
                .get().addOnSuccessListener {
                    var latestViews = it.getLong("views")
                    if (latestViews == null) {
                        latestViews = 1L
                    }else{
                        latestViews = latestViews + 1
                    }

                    Firebase.firestore.collection(SONGS).document(id)
                        .update(mapOf("views" to latestViews))
                }

        }
    }
}