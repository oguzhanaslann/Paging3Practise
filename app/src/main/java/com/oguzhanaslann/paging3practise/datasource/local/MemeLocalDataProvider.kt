package com.oguzhanaslann.paging3practise.datasource.local

import com.oguzhanaslann.paging3practise.datasource.local.room.dao.MemeDao

class MemeLocalDataProvider(
    private val memeDao: MemeDao
) : MemeLocalSource {

}
