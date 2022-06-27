package com.oguzhanaslann.paging3practise.ui

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.oguzhanaslann.paging3practise.datasource.local.room.db.MemeDB
import com.oguzhanaslann.paging3practise.datasource.local.room.entity.MemeEntity
import com.oguzhanaslann.paging3practise.datasource.local.room.entity.RemoteKeys
import com.oguzhanaslann.paging3practise.datasource.network.MemeNetworkSource
import com.oguzhanaslann.paging3practise.domain.Meme

const val INIT_PAGE = 0
const val PAGE_SIZE = 5

@OptIn(ExperimentalPagingApi::class)
class MemeRemoteMediator(
    val memeNetworkSource: MemeNetworkSource,
    val memeLocalSource: MemeDB
) : RemoteMediator<Int, Meme>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Meme>): MediatorResult {
        Log.e("TAG", "load: ", )
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INIT_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val memesResult: Result<List<Meme>> =
                memeNetworkSource.getMemes(page, state.config.pageSize)
            val memes = memesResult.getOrDefault(emptyList())
            val endOfPaginationReached = memes.isEmpty()
            if (loadType == LoadType.REFRESH) {
                memeLocalSource.runInTransaction {
                    memeLocalSource.remoteKeysDao().clearRemoteKeys()
                    memeLocalSource.memeDao().clear()
                }
            }
            val prevKey = if (page == INIT_PAGE) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = memes.map {
                RemoteKeys(memeId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            memeLocalSource.remoteKeysDao().insertAll(keys)
            val memeEntities: List<MemeEntity> = emptyList()
            memeLocalSource.memeDao().insert(memeEntities)
//            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Meme>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                memeLocalSource.remoteKeysDao().remoteKeysMemeId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Meme>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                memeLocalSource.remoteKeysDao().remoteKeysMemeId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Meme>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                memeLocalSource.remoteKeysDao().remoteKeysMemeId(repoId)
            }
        }
    }
}
