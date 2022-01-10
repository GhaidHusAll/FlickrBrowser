package com.example.flicker_ghh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.flicker_ghh.databinding.ActivityFavoritesBinding
import com.example.flicker_ghh.model.Photo
import com.example.flicker_ghh.roomDB.DatabaseFavPhoto
import com.example.flicker_ghh.roomDB.PhotoRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var myList: List<PhotoRoom>
    private lateinit var adapter: AdapterPhotoRoom
    private val dao by lazy { DatabaseFavPhoto.setUpDatabase(this).myDao() }
    private var selectedPhoto: PhotoRoom? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        myList= arrayListOf()
        adapter = AdapterPhotoRoom(myList,this@FavoritesActivity)
        binding.mainRVF.adapter = adapter
        binding.mainRVF.layoutManager = GridLayoutManager(this@FavoritesActivity,3)
        fetch()
        binding.btnUnFav.setOnClickListener {
            unlike()
        }
        binding.btnBackToMain.setOnClickListener {
            val toMain = Intent(this,MainActivity::class.java)
            startActivity(toMain)
        }

    }
    private fun fetch(){
        CoroutineScope(IO).launch {
            val list = async { dao.fetch() }.await()
            if (list.isNotEmpty()){
                withContext(Main){
                   adapter.updateRecyclerView(list)
                }
            }else{
                Toast.makeText(this@FavoritesActivity,"there is no data", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun displayMood(photo: PhotoRoom){
        selectedPhoto = photo
        //c for large
        val urlBig = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_c.jpg"

        binding.mainRVF.isVisible = false
        binding.btnBackToMain.isVisible = false
        binding.llDisplayF.isVisible = true

        binding.tvDisplayF.text = photo.title
        Glide.with(this).load(urlBig).into(binding.ivDisplayF)

        binding.btnDisplayBack.setOnClickListener {
            selectedPhoto = null
            binding.mainRVF.isVisible = true
            binding.btnBackToMain.isVisible = true
            binding.llDisplayF.isVisible = false
        }

    }
    private fun unlike(){
        if (selectedPhoto != null){
            CoroutineScope(IO).launch {
                val isDelete = dao.delete(
                    PhotoRoom(selectedPhoto?.pk!!,selectedPhoto?.id!!,selectedPhoto?.owner!!,
                        selectedPhoto?.secret!!,selectedPhoto?.server!!,selectedPhoto?.farm!!,
                        selectedPhoto?.title!!,selectedPhoto?.isPublic!!,selectedPhoto?.isFriend!!,
                        selectedPhoto?.isFamily!!)
                )
                withContext(Main){
                    if (isDelete > 0){
                        binding.btnUnFav.setImageResource(R.drawable.like)
                        fetch()
                    }else{
                        Toast.makeText(this@FavoritesActivity,"Something went wrong",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}