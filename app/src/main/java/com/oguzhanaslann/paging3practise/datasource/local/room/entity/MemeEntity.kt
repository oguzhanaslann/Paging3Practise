package com.oguzhanaslann.paging3practise.datasource.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") // to prevent İ-I confusion
    val id: String,
    val url: String
)
