package com.example.flicker_ghh


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.flicker_ghh.api.APIs
import com.example.flicker_ghh.api.Client
import com.example.flicker_ghh.databinding.ActivityMainBinding
import com.example.flicker_ghh.model.Photo
import com.example.flicker_ghh.model.RSP
import com.example.flicker_ghh.roomDB.DatabaseFavPhoto
import com.example.flicker_ghh.roomDB.PhotoRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var myList: ArrayList<Photo>
    private lateinit var adapter: AdapterPhoto
    private val dao by lazy { DatabaseFavPhoto.setUpDatabase(this).myDao() }
    private var selectedPhoto: Photo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        myList= arrayListOf()
        adapter = AdapterPhoto(myList,this)
        binding.mainRV.adapter = adapter
        binding.mainRV.layoutManager = GridLayoutManager(this,3)
        requestWithSearch("nature")

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("MAIN","11$query")
                requestWithSearch(query)
                return false
            }
        })
        binding.btnFav.setOnClickListener {
            like()
        }
        binding.btnToFavs.setOnClickListener {
            val toFavs = Intent(this,FavoritesActivity::class.java)
            startActivity(toFavs)
        }

    }

    private fun requestWithSearch(search: String){
        val myAPI = Client().request()?.create(APIs::class.java)
        myAPI?.getPhoto(search,"flickr.photos.search")?.enqueue(object: Callback<RSP> {
            override fun onResponse(call: Call<RSP>, response: Response<RSP>) {
                try {
                    val result = response.body()!!.photos
                    Log.d("MAIN", "  \n${result}")
                    myList.clear()
                    for (item in result!!.photos!!) {
                        if (item.farm!!.toInt() != 0) {
                            myList.add(
                                Photo(
                                   item.id,item.owner,item.secret,item.server, item.farm,
                                    item.title,item.isPublic,item.isFriend,item.isFamily
                                )
                            )
                        }
                    }
                    adapter.updateRecyclerView(myList)
                }catch (e:Exception){
                    Log.d("MAIN","something went wrong1 ${e.localizedMessage}")
                }
            }

            override fun onFailure(call: Call<RSP>, t: Throwable) {
                Log.d("MAIN","something went wrong ${t.localizedMessage}")
                Toast.makeText(this@MainActivity,"Not Able to Load Result",Toast.LENGTH_LONG).show()
            }


        })
    }
    fun displayMood(photo: Photo){
        selectedPhoto = photo
        //c for large
        val urlBig = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_c.jpg"

        binding.mainRV.isVisible = false
        binding.searchView.isVisible = false
        binding.btnToFavs.isVisible = false
        binding.llDisplay.isVisible = true

        binding.tvDisplay.text = photo.title
        Glide.with(this).load(urlBig).into(binding.ivDisplay)

        binding.btnDisplayBack.setOnClickListener {
            selectedPhoto = null
            binding.mainRV.isVisible = true
            binding.searchView.isVisible = true
            binding.btnToFavs.isVisible = true
            binding.llDisplay.isVisible = false
        }

    }
    private fun like(){
        if (selectedPhoto != null){
            CoroutineScope(IO).launch {
           val isAdd = dao.add(PhotoRoom(0,selectedPhoto?.id!!,selectedPhoto?.owner!!,
                selectedPhoto?.secret!!,selectedPhoto?.server!!,selectedPhoto?.farm!!,
                selectedPhoto?.title!!,selectedPhoto?.isPublic!!,selectedPhoto?.isFriend!!,
                selectedPhoto?.isFamily!!))
                withContext(Main){
                    if (isAdd > 0){
                        binding.btnFav.setImageResource(R.drawable.liked)

                    }else{
                        Toast.makeText(this@MainActivity,"Something went wrong add",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}