package com.oguzhanaslann.paging3practise.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.oguzhanaslann.paging3practise.databinding.ItemMemeBinding
import com.oguzhanaslann.paging3practise.domain.Meme

class MemeAdapter : PagingDataAdapter<Meme, MemeAdapter.Holder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMemeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.onBind(currentItem)
        }
    }

    inner class Holder(val binding: ItemMemeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(currentItem: Meme) = binding.run {
            memeImage.load(currentItem)
            memeText.text = currentItem.id
        }

    }

    class DiffCallBack : DiffUtil.ItemCallback<Meme>() {
        override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean = oldItem == newItem
    }
}
