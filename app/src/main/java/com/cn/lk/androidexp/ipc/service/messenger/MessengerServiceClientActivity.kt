package com.cn.lk.androidexp.ipc.service.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_messenger_service_client.*
import java.lang.Exception
import java.lang.ref.WeakReference


class MessengerServiceClientActivity : FragmentActivity(), View.OnClickListener {

    companion object {
        var TAG = "MessengerCountService-Client"  // 23个字符以上需要用var
        val MSG_REPLY_COUNT = 2
    }

    // 用于向Service端发送消息的Messenger
    var messenger: Messenger? = null
    //用于向Service端发送消息的Messenger
    var receiver = Messenger(MsgReceiver(this))
    var isBound = false

    class MsgReceiver(activity: MessengerServiceClientActivity) : Handler() {
        /**
         * 使用弱引用，以在service的进程中释放引用
         */
        private var activity: WeakReference<MessengerServiceClientActivity>? = null

        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                MSG_REPLY_COUNT -> {
                    var act = activity!!.get()
                    if (act != null) {
                        var count = msg.data!!.getInt("count", -1)
                        act.tv.text = """${act.tv.text}
                            |receive from service--count=$count""".trimMargin()
                    }
                }
                else -> super.handleMessage(msg)
            }
        }

        init {
            this.activity = WeakReference(activity)
        }
    }


    private val connection: ServiceConnection? = object : ServiceConnection {
        /**
         * Android 系统会在与服务的连接意外中断时（例如当服务崩溃或被终止时）调用该方法。
         * 注意:当客户端取消绑定时，系统“绝对不会”调用该方法。
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
            isBound = false
            messenger = null
        }

        override fun onServiceConnected(name: ComponentName?, ser: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            if (ser != null) {
                isBound = true
                messenger = Messenger(ser)
            }
        }

        override fun onBindingDied(name: ComponentName?) {
            Log.d(TAG, "onBindingDied")
            isBound = false
            messenger = null
        }
    }

    override fun onClick(v: View?) {
        var intent = Intent(this, MessengerCountService::class.java)

        when (v) {
            btn_start -> startService(intent)
            btn_stop -> stopService(intent)

        // flags则是指定绑定时是否自动创建Service。0代表不自动创建、BIND_AUTO_CREATE则代表自动创建。
            btn_bind -> {
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
            btn_unbind -> {
                if (isBound) {
                    messenger = null
                    isBound = false
                    unbindService(connection)
                }
            }

            btn_get_count -> {
                if (isBound) {
                    val msg = Message.obtain(null, MessengerCountService.MSG_GET_COUNT)
                    msg.replyTo = receiver
                    try {
                        Log.e(TAG, "send message--" + application)
                        messenger!!.send(msg)
                        tv.text = """${tv.text.toString()}
send msg"""
                    } catch (e: Exception) {
                        Log.e(TAG, "send message error:" + e.message)
                    }
                }
            }
            else -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_service_client)

        tv.text = """MessengerServiceClientActivity onCreate
            |远程服务在另一个进程，因此application也是不同的，服务所在application会是新建的
            |Messenger需在handler中使用service以及client的弱引用，以不会导致在不同线程中内存泄漏""".trimMargin()
        btn_start.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
        btn_bind.setOnClickListener(this)
        btn_unbind.setOnClickListener(this)
        btn_get_count.setOnClickListener(this)
    }
}