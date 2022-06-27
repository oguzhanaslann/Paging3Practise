package com.oguzhanaslann.paging3practise.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oguzhanaslann.paging3practise.datasource.repository.MemeRepository
import com.oguzhanaslann.paging3practise.domain.Meme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class MemeViewModel(
    private val memeRepository: MemeRepository
) : ViewModel() {

    fun getMemes() : Flow<PagingData<Meme>> {
        return memeRepository.memePagingStream()
            .onEach {
                Log.e("TAG", "getMemes: it $it")
            }
            .cachedIn(viewModelScope)
    }
}
