package com.oguzhanaslann.paging3practise.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhanaslann.paging3practise.databinding.ActivityMainBinding
import com.oguzhanaslann.paging3practise.datasource.local.room.db.MemeDB
import org.koin.android.ext.android.get
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
            }
        }

//        val appDatabase: MemeDB = get()

        lifecycleScope.launchWhenStarted {
            memeViewModel.getMemes()
                .collect {
                    Log.e("TAG", "onCreate: it $it" )
                    memeAdapter.submitData(it)
                }

        }
    }
}
