package com.oguzhanaslann.paging3practise.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.oguzhanaslann.paging3practise.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val memeViewModel: MemeViewModel by viewModel()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }


    private val memeAdapter by lazy {
        MemeAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            rvMemes.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = memeAdapter
                LinearSnapHelper().attachToRecyclerView(this)
            }
        }

        lifecycleScope.launchWhenStarted {
            memeViewModel.getMemes()
                .collect {
                    memeAdapter.submitData(it)
                }

        }

        lifecycleScope.launchWhenStarted {
            memeAdapter.loadStateFlow.collect {
//                Log.e("TAG", "onCreate: it.source ${it.source}" )
//                Log.e("TAG", "onCreate: ")
//                Log.e("TAG", "onCreate: it.mediator ${it.mediator}" )
            }
        }
    }
}
