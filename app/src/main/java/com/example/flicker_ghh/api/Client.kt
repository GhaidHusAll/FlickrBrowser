package com.example.flicker_ghh.api

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class Client {

    private var retrofitVar: Retrofit? = null

    fun request():Retrofit?{
        retrofitVar = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/services/rest/").
            addConverterFactory(SimpleXmlConverterFactory.create()).build()
        return retrofitVar
    }
}