package com.cn.lk.androidexp.ipc.service.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import java.lang.Exception
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

// !!必须搭配try
class MessengerCountService : Service() {
    companion object {
        val TAG = "MessengerCountService"
        val MSG_GET_COUNT = 1
    }

    /**
     * 公共方法
     * @return
     */
    var count = AtomicInteger(0)
        private set
    private var quit: Boolean = false
    private var messenger: Messenger? = Messenger(CountHandle(this))

    /**
     * 用于接收从客户端传递过来的数据
     */
    inner class CountHandle(service: MessengerCountService) : Handler() {
        /**
         * 使用弱引用，以不会导致在client线程内存泄漏
         */
        private var service: WeakReference<MessengerCountService>? = null

        override fun handleMessage(msg: Message?) {

            when (msg!!.what) {
                MSG_GET_COUNT -> {
                    Log.i(TAG, "Receive MSG_GET_COUNT--" + application)

                    var receiver = msg.replyTo
                    var msg = Message.obtain(null, MessengerServiceClientActivity.MSG_REPLY_COUNT)
                    var data = Bundle()
                    data.putInt("count", service!!.get()!!.count.get())
                    msg.data = data
                    try {
                        receiver.send(msg)
                    } catch (e :Exception) {
                        Log.e(TAG, "send reply msg error--" + e.message)
                    }
                }
                else -> super.handleMessage(msg)
            }
        }

        init {
            this.service = WeakReference(service)
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