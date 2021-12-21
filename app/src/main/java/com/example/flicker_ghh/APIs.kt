package com.example.flicker_ghh

import retrofit2.Call
import retrofit2.http.*

interface APIs {


    @GET("?api_key=357c66a684e6756858b09fb87b8f8be5&format=json&nojsoncallback=1")
    fun getPhoto(@Query("tags")search: String,@Query("method")method: String): Call<Photo>

}