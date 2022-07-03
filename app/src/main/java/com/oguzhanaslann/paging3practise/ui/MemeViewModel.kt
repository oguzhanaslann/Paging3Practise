package com.oguzhanaslann.paging3practise.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.oguzhanaslann.paging3practise.datasource.repository.MemeRepository
import com.oguzhanaslann.paging3practise.domain.Meme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MemeViewModel(
    private val memeRepository: MemeRepository
) : ViewModel() {

    fun getMemes() : Flow<PagingData<Meme>> {
        return memeRepository.memePagingStream()
            .cachedIn(viewModelScope)
            .onEach {
                Log.e("TAG", "getMemes: it $it")
            }.onEach {
                it.map {
                    Log.e("TAGG", "getMemes: it.map $it")
                    it
                }
            }
    }
}
