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
import com.vanshpunia.melody.databinding.SongDesignBinding
import com.vanshpunia.melody.models.Song
import com.vanshpunia.melody.utils.SONGS

class SongAdapter(var context: Context, var songList: List<String>?, var category: String?) :
    RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: SongDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SongDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Firebase.firestore.collection(SONGS).document(songList!![position]).get()
            .addOnSuccessListener { song->
                holder.binding.songTitle.text = song.get("title").toString()
                holder.binding.singerName.text = song.get("singer").toString()
                Glide.with(context).load(song.get("coverUrl").toString())
                    .apply(
                        RequestOptions().transform(RoundedCorners(32))
                    )
                    .into(holder.binding.songCover)

                holder.binding.root.setOnClickListener {
                    MyExoPlayer.startPlaying(holder.binding.root.context, song.toObject<Song>()!!)
                    holder.binding.root.context.startActivity(Intent(context, PlayerActivity::class.java))
                    PlayerActivity.category = category!!
                }
            }
    }

}