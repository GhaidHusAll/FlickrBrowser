package com.example.flicker_ghh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.flicker_ghh.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var myList: ArrayList<PhotoX>
    private lateinit var adapter: AdapterPhoto
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

    }

    private fun requestWithSearch(search: String){
        val myAPI = Client().request()?.create(APIs::class.java)
        myAPI?.getPhoto(search,"flickr.photos.search")?.enqueue(object: Callback<Photo> {
            override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                val result = response.body()!!.photos.photo
                Log.d("MAIN","  \n${result}")
                myList.clear()
                for (item in result){
                    if (item.farm!=0){
                        myList.add(
                            PhotoX(item.farm,
                            item.id,item.isfamily,item.isfriend,item.ispublic,
                            item.owner,item.secret,item.server,item.title))
                    }
                }
                adapter.updateRecyclerView(myList)
            }

            override fun onFailure(call: Call<Photo>, t: Throwable) {
                Log.d("MAIN","something went wrong")
                Toast.makeText(this@MainActivity,"Not Able to Load Result",Toast.LENGTH_LONG).show()
            }


        })
    }
    fun displayMood(url:String,title:String){
        binding.mainRV.isVisible = false
        binding.searchView.isVisible = false
        binding.llDisplay.isVisible = true

        binding.tvDisplay.text = title
        Glide.with(this).load(url).into(binding.ivDisplay)



        binding.btnDisplayBack.setOnClickListener {
            binding.mainRV.isVisible = true
            binding.searchView.isVisible = true
            binding.llDisplay.isVisible = false
        }

    }
}