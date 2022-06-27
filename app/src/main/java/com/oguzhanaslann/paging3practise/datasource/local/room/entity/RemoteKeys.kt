package com.oguzhanaslann.paging3practise.datasource.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val memeId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
