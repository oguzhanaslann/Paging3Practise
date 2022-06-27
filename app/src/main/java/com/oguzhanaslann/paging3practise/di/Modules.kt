package com.oguzhanaslann.paging3practise.di

import com.oguzhanaslann.paging3practise.datasource.local.MemeLocalDataProvider
import com.oguzhanaslann.paging3practise.datasource.local.MemeLocalSource
import com.oguzhanaslann.paging3practise.datasource.local.room.db.MemeDB
import com.oguzhanaslann.paging3practise.datasource.network.MemeNetworkDataProvider
import com.oguzhanaslann.paging3practise.datasource.network.MemeNetworkSource
import com.oguzhanaslann.paging3practise.datasource.repository.MemeRepository
import com.oguzhanaslann.paging3practise.ui.MemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val networkModule = module {
    single {
        MemeNetworkDataProvider() as MemeNetworkSource
    }
}

val localModule = module {
    single {
        MemeDB.getDatabase(androidContext())
    }

    single {
        val db: MemeDB = get()
        MemeLocalDataProvider(db.memeDao()) as MemeLocalSource
    }
}

val repositoryModule = module {
    factory {
        MemeRepository(
            memeNetworkSource = get(),
            memeLocalSource = get()
        )
    }
}

val viewModelModule = module {
    viewModel {
        MemeViewModel(
            get()
        )
    }
}

val appModules = listOf<Module>(viewModelModule, repositoryModule, networkModule, localModule)
