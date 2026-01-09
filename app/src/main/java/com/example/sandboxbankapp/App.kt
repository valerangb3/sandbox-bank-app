package com.example.sandboxbankapp

import android.app.Application
import com.example.sandboxbankapp.authorize.di.authorizeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                authorizeViewModelModule
            )
        }
    }
}