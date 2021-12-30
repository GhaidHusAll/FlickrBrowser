package com.example.flicker_ghh

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flicker_ghh.databinding.RowBinding
import com.example.flicker_ghh.model.Photo

class AdapterPhoto(var list: ArrayList<Photo>, private val activity: Activity):RecyclerView.Adapter<AdapterPhoto.HolderAdapter>() {
    class HolderAdapter(val binding: RowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderAdapter {
       return HolderAdapter(RowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HolderAdapter, position: Int) {
        val item = list[position]
        //q for cropped square
        val url = "https://farm${item.farm}.staticflickr.com/${item.server}/${item.id}_${item.secret}_q.jpg"
        holder.binding.apply {
            tvTitle.text = item.title
            Glide.with(activity).load(url).into(ivImage)
        }
        holder.binding.cv.setOnClickListener {
                (activity as MainActivity).displayMood(item)
        }
    }

    override fun getItemCount() = list.size

    fun updateRecyclerView(newList: ArrayList<Photo>){
        this.list = newList
        notifyDataSetChanged()
    }
}