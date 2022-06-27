package com.oguzhanaslann.paging3practise.datasource.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oguzhanaslann.paging3practise.datasource.local.room.entity.MemeEntity
import com.oguzhanaslann.paging3practise.domain.Meme

@Dao
interface MemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meme: MemeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg memes: MemeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memes: List<MemeEntity>)

    @Query("select * from meme")
    fun getMemes() : PagingSource<Int, Meme>

    @Query("delete from meme")
    fun clear()
}
