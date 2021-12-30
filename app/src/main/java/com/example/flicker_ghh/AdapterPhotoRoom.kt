package com.example.flicker_ghh

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flicker_ghh.databinding.RowfBinding
import com.example.flicker_ghh.roomDB.PhotoRoom

class AdapterPhotoRoom(var list: List<PhotoRoom>, private val activity: Activity):RecyclerView.Adapter<AdapterPhotoRoom.HolderAdapter>() {
    class HolderAdapter(val binding: RowfBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderAdapter {
       return HolderAdapter(RowfBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HolderAdapter, position: Int) {
        val item = list[position]
        println("-------${item.title}")
        //q for cropped square
        val url = "https://farm${item.farm}.staticflickr.com/${item.server}/${item.id}_${item.secret}_q.jpg"
        holder.binding.apply {
            tvTitleF.text = item.title
            Glide.with(activity).load(url).into(ivImageF)
        }
        holder.binding.cvF.setOnClickListener {

                (activity as FavoritesActivity).displayMood(item)

        }
    }

    override fun getItemCount() = list.size

    fun updateRecyclerView(newList: List<PhotoRoom>){
        this.list = newList
        notifyDataSetChanged()
    }
}