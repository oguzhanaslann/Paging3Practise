package com.oguzhanaslann.paging3practise

import android.app.Application
import com.oguzhanaslann.paging3practise.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }
}
