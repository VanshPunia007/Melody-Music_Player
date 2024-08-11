package com.vanshpunia.melody

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.vanshpunia.melody.databinding.ActivityPlayerBinding
import androidx.media3.ui.PlayerView


class PlayerActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPlayerBinding.inflate(layoutInflater)
    }
    companion object {
        lateinit var category: String
    }
    lateinit var exoPlayer: ExoPlayer

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(binding.root)

        MyExoPlayer.currentSong()?.apply {
            binding.songName.text = title
            binding.singerName.text = singer
            Glide.with(this@PlayerActivity).load(coverUrl)
                .circleCrop()
                .into(binding.songImage)
            exoPlayer = MyExoPlayer.getInstance()!!
            binding.playerView.player = exoPlayer
            binding.playerView.showController()
            binding.playlist.text = category
        }
    }
}