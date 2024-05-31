package com.tomato.compose

import android.app.Application

/**
 * Created by Tomato on 2024/5/31.
 * Description：
 */
class App :Application() {

    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }




}