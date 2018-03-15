package com.cn.lk.androidexp.ipc.service.messenger

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread


class MessengerCountService : Service() {
    companion object {
        val TAG = "MessengerCountService"
        val MSG_GET_COUNT = 1
    }

    /**
     * 公共方法
     * @return
     */
    public var count = AtomicInteger(0)
        private set
    private var quit: Boolean = false
    private var messenger: Messenger? = Messenger(CountHandle())

    /**
     * 用于接收从客户端传递过来的数据
     */
    inner class CountHandle : Handler() {
        override fun handleMessage(msg: Message?) {

            when (msg!!.what) {
                MSG_GET_COUNT -> Log.i(TAG, "Receive MSG_GET_COUNT")
                else -> super.handleMessage(msg)
            }
//        this@MessengerCountService
        }
    }

    /**
     * 把Binder类返回给客户端
     */
    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind")
        return messenger!!.binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreated")
        thread(start = true) {
            // 每间隔一秒count加1 ，直到quit为true。
            while (!quit) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                count.addAndGet(1)
            }
        }
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
        this.messenger = null  // 对象必须释放掉，保证及时gc掉
    }
}