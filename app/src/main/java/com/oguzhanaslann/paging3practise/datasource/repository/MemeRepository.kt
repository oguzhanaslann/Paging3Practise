package com.oguzhanaslann.paging3practise.datasource.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oguzhanaslann.paging3practise.datasource.local.room.db.MemeDB
import com.oguzhanaslann.paging3practise.datasource.network.MemeNetworkSource
import com.oguzhanaslann.paging3practise.domain.Meme
import com.oguzhanaslann.paging3practise.ui.MemeRemoteMediator
import com.oguzhanaslann.paging3practise.ui.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class MemeRepository(
    val memeNetworkSource: MemeNetworkSource,
    val memeLocalSource: MemeDB
) {

    fun memePagingStream(): Flow<PagingData<Meme>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MemeRemoteMediator(
                memeNetworkSource = memeNetworkSource,
                memeLocalSource = memeLocalSource
            ),
            pagingSourceFactory = {
                memeLocalSource.memeDao().getMemes()
            }
        ).flow
    }
}
