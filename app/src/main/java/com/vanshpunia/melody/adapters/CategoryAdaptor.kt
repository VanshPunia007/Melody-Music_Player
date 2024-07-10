package com.vanshpunia.melody.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanshpunia.melody.databinding.CategoryDesignBinding
import com.vanshpunia.melody.models.Category

class CategoryAdaptor(var context: Context, var categoryList: ArrayList<Category>): RecyclerView.Adapter<CategoryAdaptor.ViewHolder>() {
    inner class ViewHolder(var binding: CategoryDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(categoryList.get(position).coverurl).into(holder.binding.categoryImage)
        holder.binding.categoryName.text = categoryList.get(position).name
    }
}