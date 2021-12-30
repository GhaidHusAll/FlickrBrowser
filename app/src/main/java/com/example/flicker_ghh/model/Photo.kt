package com.example.flicker_ghh.model

import org.simpleframework.xml.*
import java.io.Serializable

@Root(name = "photo", strict = false)
class Photo @JvmOverloads constructor(


    @field:Attribute(name = "id",required = false)
    var id: String? = null,

    @field:Attribute(name = "owner",required = false)
    var owner: String? = null,

    @field:Attribute(name = "",required = false)
    var secret: String? = null,

    @field:Attribute(name = "server",required = false)
    var server: String? = null,

    @field:Attribute(name = "farm",required = false)
    var farm: String? = null,

    @field:Attribute(name = "title", required = false)
    var title: String? = null,

    @field:Attribute(name = "ispublic", required = false)
    var isPublic: String? = null,

    @field:Attribute(name = "isfriend", required = false)
    var isFriend: String? = null,

    @field:Attribute(name = "isfamily", required = false)
    var isFamily: String? = null


):Serializable{
}