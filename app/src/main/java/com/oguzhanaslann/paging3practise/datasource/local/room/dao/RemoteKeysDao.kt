package com.oguzhanaslann.paging3practise.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oguzhanaslann.paging3practise.datasource.local.room.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE memeId = :memeId")
    fun remoteKeysMemeId(memeId: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    fun clearRemoteKeys()
}
