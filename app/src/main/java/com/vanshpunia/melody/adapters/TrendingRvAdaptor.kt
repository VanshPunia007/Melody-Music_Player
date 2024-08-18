package com.vanshpunia.melody.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.vanshpunia.melody.MyExoPlayer
import com.vanshpunia.melody.PlayerActivity
import com.vanshpunia.melody.SongListActivity
import com.vanshpunia.melody.databinding.TrendingDesignBinding
import com.vanshpunia.melody.models.Song
import com.vanshpunia.melody.utils.SONGS

class TrendingRvAdaptor(var context: Context, var songList: List<String>?):
    RecyclerView.Adapter<TrendingRvAdaptor.ViewHolder>() {

    inner class ViewHolder(var binding: TrendingDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TrendingDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Firebase.firestore.collection(SONGS).document(songList!![position]).get()
            .addOnSuccessListener { song->
                holder.binding.songName.text = song.get("title").toString()
                Glide.with(context).load(song.get("coverUrl"))
                    .apply(RequestOptions().transform(RoundedCorners(32)))
                    .into(holder.binding.songImage)

                holder.binding.root.setOnClickListener {
                    MyExoPlayer.startPlaying(context, song.toObject<Song>()!!)
                    PlayerActivity.category = "Trending"
                    context.startActivity(Intent(context,PlayerActivity::class.java))
                }
            }
    }

}