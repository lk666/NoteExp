package com.cn.lk.androidexp.ipc.service.binder

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


class CountService : Service() {
    companion object {
        var TAG = "BookService-Client"
    }

    /**
     * 公共方法
     * @return
     */
    public var count: Int = 0
        private set
    private var quit: Boolean = false
    private var thread: Thread? = null
    private val binder = CountBinder()

    /**
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     */
    inner class CountBinder : Binder() {
        // 声明一个方法，getService。（提供给客户端调用）
        internal// 返回当前对象LocalService,这样我们就可在客户端端调用Service的公共方法了
        // 特别注意，此处不要直接返回
        val service: CountService
            get() = this@CountService
    }

    /**
     * 把Binder类返回给客户端
     */
    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreated")
        thread = Thread(Runnable {
            // 每间隔一秒count加1 ，直到quit为true。
            while (!quit) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                count++
            }
        })
        thread!!.start()
    }

    /**
     * 解除绑定时调用
     * @return
     */
    override fun onUnbind(intent: Intent): Boolean {
        Log.i(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.i(TAG, "odDestroyed")
        this.quit = true
        super.onDestroy()
    }
}