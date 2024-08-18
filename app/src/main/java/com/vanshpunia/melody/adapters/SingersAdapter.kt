package com.vanshpunia.melody.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.vanshpunia.melody.SignUpActivity
import com.vanshpunia.melody.SongListActivity
import com.vanshpunia.melody.databinding.CategoryDesignBinding
import com.vanshpunia.melody.models.Category

class SingersAdapter(var context: Context, var singersList: ArrayList<Category>) :
    RecyclerView.Adapter<SingersAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: CategoryDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return singersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(singersList.get(position).coverUrl)
            .apply(
                RequestOptions().transform(RoundedCorners(32))
            )
            .into(holder.binding.categoryImage)
        holder.binding.categoryName.text = singersList[position].name
        holder.binding.root.setOnClickListener {
            SongListActivity.category = singersList[position]
            context.startActivity(Intent(context, SongListActivity::class.java))
        }
    }
}