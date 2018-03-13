package com.cn.lk.androidexp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BookService : Service() {
    val TAG = "BookService"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    // ==============================================================================




    override fun onBind(intent: Intent?): IBinder? = null

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onRebind")
        return super.onUnbind(intent)
    }

}