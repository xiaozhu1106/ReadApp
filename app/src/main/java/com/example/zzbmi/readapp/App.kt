package com.example.zzbmi.readapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @author ZhuZiBo
 * @date 2017/12/7
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}
