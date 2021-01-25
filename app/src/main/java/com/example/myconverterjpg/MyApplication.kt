package com.example.myconverterjpg

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {
        private var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    fun getAppContext(): Context? {
        return context
    }
}