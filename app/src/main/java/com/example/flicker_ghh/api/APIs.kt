package com.example.flicker_ghh.api

import com.example.flicker_ghh.model.RSP
import retrofit2.Call
import retrofit2.http.*

interface APIs {

    @GET("?api_key=9347fb2988a3f33c2bfb443d247b91a4&nojsoncallback=1")
    fun getPhoto(@Query("tags")search: String,@Query("method")method: String): Call<RSP>

}