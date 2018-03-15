package com.cn.lk.androidexp.ipc.service.binder

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.concurrent.atomic.AtomicInteger


class BinderCountService : Service() {
    companion object {
        var TAG = "BinderCountService"
    }

    /**
     * 公共方法
     * @return
     */
    public var count = AtomicInteger(0)
        private set
    private var quit: Boolean = false
    private var binder:CountBinder? = CountBinder()

    /**
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     */
    inner class CountBinder : Binder() {
        // 声明一个方法，getService。（提供给客户端调用）
        internal// 返回当前对象LocalService,这样我们就可在客户端端调用Service的公共方法了
        // 特别注意，此处不要直接返回
        val service: BinderCountService
            get() = this@BinderCountService
    }

    /**
     * 把Binder类返回给客户端
     */
    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreated")
        Thread(Runnable {
            // 每间隔一秒count加1 ，直到quit为true。
            while (!quit) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                listener!!.onTick(count.addAndGet(1))
            }
        }).start()
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
        super.onDestroy()
        Log.i(TAG, "odDestroyed")
        this.quit = true
        this.binder = null  // 对象必须释放掉，保证及时gc掉
    }

    // 异步回调
    interface IListener {
        fun onTick(curCount:Int)
    }

    var listener:IListener? = null // 可能内存泄露，此处为强引用
}