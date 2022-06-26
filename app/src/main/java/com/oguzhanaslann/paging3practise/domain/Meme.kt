package com.oguzhanaslann.paging3practise.domain

import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
data class Meme(
    val id : String,
    val url : String
) : Serializable
