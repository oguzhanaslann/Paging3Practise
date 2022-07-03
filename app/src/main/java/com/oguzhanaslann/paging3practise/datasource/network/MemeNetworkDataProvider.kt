package com.oguzhanaslann.paging3practise.datasource.network

import android.util.Log
import com.oguzhanaslann.paging3practise.domain.Meme
import java.util.*

class MemeNetworkDataProvider : MemeNetworkSource {
    override suspend fun getMemes(page: Int, pageSize: Int): Result<List<Meme>> {
        val list = mutableListOf<Meme>()
        if (page <= 20) {
            repeat(pageSize) {
                Meme(
                    id = "${page}-${it}",
                    url = "https://picsum.photos/id/1/400/400"
                ).also {
                    list.add(it)
                }
            }
        }

        return Result.success(list).also {
            Log.e("TAG", "getMemes: $it")
        }
    }
}
