package com.example.flicker_ghh

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {

    private var retrofitVar: Retrofit? = null

    fun request():Retrofit?{
        retrofitVar = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/services/rest/").
            addConverterFactory(GsonConverterFactory.create()).build()
        return retrofitVar
    }
}