package com.example.weather.application

import android.app.Application
import android.content.Context
import com.example.weather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApplication : Application() {
    companion object {
        private var instance: Application? = null
        fun getBaseApplicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin()
    }

    private fun initKoin(){
        startKoin {
            androidContext(this@WeatherApplication)
            androidLogger(Level.ERROR)
            modules(listOf(appModule))
        }
    }
}