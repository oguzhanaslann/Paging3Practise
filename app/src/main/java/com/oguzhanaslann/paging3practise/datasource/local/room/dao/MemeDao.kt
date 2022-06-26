package com.oguzhanaslann.paging3practise.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.oguzhanaslann.paging3practise.datasource.local.room.entity.MemeEntity

@Dao
interface MemeDao {

    @Insert
    fun insert(meme: MemeEntity)

    @Insert
    fun insert(vararg memes: MemeEntity)

    @Insert
    fun insert(memes: List<MemeEntity>)

}
