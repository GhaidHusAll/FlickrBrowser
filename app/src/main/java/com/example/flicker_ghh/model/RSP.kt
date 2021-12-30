package com.example.flicker_ghh.model

import org.simpleframework.xml.*
import java.io.Serializable

@Root(name = "rsp",strict = false)
 class RSP constructor(): Serializable{
    @field:Element(name = "photos")
    var photos: Photos? = null
}