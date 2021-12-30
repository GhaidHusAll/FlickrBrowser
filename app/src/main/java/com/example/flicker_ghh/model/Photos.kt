package com.example.flicker_ghh.model

import org.simpleframework.xml.*
import java.io.Serializable


@Root(name = "photos" , strict = false)
class Photos @JvmOverloads constructor(

    @field:ElementList(name = "photo",inline = true )
    var photos: ArrayList<Photo>? = null

):Serializable {
}