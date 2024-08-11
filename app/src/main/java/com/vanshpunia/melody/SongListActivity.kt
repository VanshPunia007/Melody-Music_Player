package com.vanshpunia.melody

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.vanshpunia.melody.adapters.SongAdapter
import com.vanshpunia.melody.databinding.ActivitySongListBinding
import com.vanshpunia.melody.models.Category
import com.vanshpunia.melody.models.Song

class SongListActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySongListBinding.inflate(layoutInflater)
    }

    companion object {
        lateinit var category: Category
    }
    var songList = ArrayList<Song>()

    lateinit var songAdapter : SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(binding.root)

        binding.categoryName.text = category.name
        Glide.with(this).load(category.coverurl)
            .apply(
                RequestOptions().transform(RoundedCorners(32))
            )
            .into(binding.categoryImage)

        setSongs()
    }

    private fun setSongs() {
        songAdapter = SongAdapter(this@SongListActivity, category.songs, category.name)
        binding.songRV.adapter = songAdapter
        binding.songRV.layoutManager = LinearLayoutManager(this@SongListActivity)
        songAdapter.notifyDataSetChanged()
    }
}