package com.example.flicker_ghh

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo:  ArrayList<PhotoX>,
    val total: Int
)