package com.vanshpunia.melody

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.vanshpunia.melody.databinding.ActivityPlayerBinding
import com.vanshpunia.melody.utils.SONGS


class PlayerActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPlayerBinding.inflate(layoutInflater)
    }
    companion object {
        lateinit var category: String
    }
    lateinit var exoPlayer: ExoPlayer

    lateinit var rotateAnimation: RotateAnimation

    var playerListener = object : Player.Listener{
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            showGif(isPlaying)

        }
    }

    override fun onResume() {
        super.onResume()
        showGif(true)
    }

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(binding.root)

        MyExoPlayer.currentSong()?.apply {
            binding.songName.text = title
            binding.singerName.text = singer
            Glide.with(this@PlayerActivity).load(R.drawable.music_gif)
                .circleCrop()
                .into(binding.songImageGif)

            rotateAnimation = RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            rotateAnimation.duration = 15000 // Duration of one rotation in milliseconds
            rotateAnimation.repeatCount = Animation.INFINITE // Repeat indefinitely
            rotateAnimation.interpolator = LinearInterpolator() // Use a linear interpolator for smooth rotation
            binding.songImage.startAnimation(rotateAnimation)

            Glide.with(this@PlayerActivity).load(coverUrl)
                .circleCrop()
                .into(binding.songImage)
            if(liked == true){
                binding.like.setImageResource(R.drawable.filled_heart)
            }
            else{
                binding.like.setImageResource(R.drawable.heart)
            }
            exoPlayer = MyExoPlayer.getInstance()!!
            binding.playerView.player = exoPlayer
            binding.playerView.showController()
            binding.playlist.text = category
            exoPlayer.addListener(playerListener)
        }

        binding.like.setOnClickListener {
            Firebase.firestore.collection(SONGS).document(MyExoPlayer.currentSong()?.id!!).get()
                .addOnSuccessListener {
                    var liked = it.get("liked") as Boolean
                    if(liked.toString() == "true"){
                        Firebase.firestore.collection(SONGS).document(MyExoPlayer.currentSong()?.id!!).update("liked", false)
                        binding.like.setImageResource(R.drawable.heart)
                    }else{
                        Firebase.firestore.collection(SONGS).document(MyExoPlayer.currentSong()?.id!!).update("liked", true)
                        binding.like.setImageResource(R.drawable.filled_heart)
                    }
                }
        }

        binding.back.setOnClickListener {
            finish()
        }
    }

    fun showGif(show : Boolean){
        if(show){
            binding.songImageGif.visibility = View.VISIBLE
            binding.songImage.startAnimation(rotateAnimation)
        }else{
            binding.songImageGif.visibility = View.INVISIBLE
            binding.songImage.clearAnimation()
        }
    }
}