package com.cn.lk.androidexp

import android.app.Application
import com.orhanobut.logger.*
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

        Thread.setDefaultUncaughtExceptionHandler(CrashHandler())

       logInit()
    }

    private fun logInit(){
        val androidLog = AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(2)
            .methodOffset(5)
//                .logStrategy("")
            .tag("DEFAULT_YAG")   //默认前缀
            .build())
        Logger.addLogAdapter(androidLog)
        Logger.addLogAdapter(DiskLogAdapter(CsvFormatStrategy.newBuilder()
                .tag("FILE")
                .build()))
    }

}