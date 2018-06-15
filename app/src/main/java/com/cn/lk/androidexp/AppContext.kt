package com.cn.lk.androidexp

import android.app.Application
import com.squareup.leakcanary.LeakCanary


class AppContext : Application() {
    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        // Normal app init code...
        instance = this
    }
}