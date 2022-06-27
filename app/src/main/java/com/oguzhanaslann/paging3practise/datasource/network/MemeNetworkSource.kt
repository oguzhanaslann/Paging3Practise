package com.oguzhanaslann.paging3practise.datasource.network

import com.oguzhanaslann.paging3practise.domain.Meme

interface MemeNetworkSource {
    suspend fun getMemes(page: Int, pageSize: Int): Result<List<Meme>>
}
