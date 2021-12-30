package com.example.flicker_ghh.roomDB

import androidx.room.*
import org.simpleframework.xml.Attribute


@Entity(tableName = "photo")
data class PhotoRoom(
    @PrimaryKey(autoGenerate = true)
    var pk: Int,
    var id: String,
    var owner: String,
    var secret: String,
    var server: String,
    var farm: String,
    var title: String,
    var isPublic: String,
    var isFriend: String,
    var isFamily: String
)
