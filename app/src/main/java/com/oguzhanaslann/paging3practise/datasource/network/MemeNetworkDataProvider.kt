package com.oguzhanaslann.paging3practise.datasource.network

import com.oguzhanaslann.paging3practise.domain.Meme

class MemeNetworkDataProvider : MemeNetworkSource {
    override suspend fun getMemes(page: Int, pageSize: Int): Result<List<Meme>> {
        val list = mutableListOf<Meme>()
        if (page <= 5) {
            repeat(pageSize) {
                Meme(
                    id = "${page + it}",
                    url = "https://picsum.photos/200/300"
                ).also {
                    list.add(it)
                }
            }
        }
        return Result.success(list)
    }
}
