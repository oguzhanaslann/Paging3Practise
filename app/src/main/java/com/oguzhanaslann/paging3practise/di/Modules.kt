package com.oguzhanaslann.paging3practise.di

import com.oguzhanaslann.paging3practise.datasource.local.room.db.MemeDB
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


val localModule = module {
    single {
        MemeDB.getDatabase(androidContext())
    }
}

val appModules = listOf<Module>()
