package com.cn.lk.androidexp.ipc.service.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_bind_service_client.*


class BinderServiceClientActivity : FragmentActivity(), View.OnClickListener {

    companion object {
        var TAG = "BinderCountService-Client"
    }

    var service: BinderCountService? = null

    private val connection: ServiceConnection? = object : ServiceConnection {
        /**
         * Android 系统会在与服务的连接意外中断时（例如当服务崩溃或被终止时）调用该方法。
         * 注意:当客户端取消绑定时，系统“绝对不会”调用该方法。
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
            service = null
        }

        override fun onServiceConnected(name: ComponentName?, ser: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            var bind = ser!! as BinderCountService.CountBinder?
            service = bind!!.service
            service!!.listener = object : BinderCountService.IListener {
                override fun onTick(curCount: Int) {
                    // 这种不确定线程的需放到异步线程
                    runOnUiThread({
                        tv.text = """${tv.text.toString()}
--${curCount}"""
                    })
                }

            }
        }

        override fun onBindingDied(name: ComponentName?) {
            Log.d(TAG, "onBindingDied")
            service = null
        }
    }


    override fun onClick(v: View?) {
        var intent = Intent(this, BinderCountService::class.java)

        when (v) {
            btn_start -> startService(intent)
            btn_stop -> stopService(intent)

        // flags则是指定绑定时是否自动创建Service。0代表不自动创建、BIND_AUTO_CREATE则代表自动创建。
            btn_bind -> {
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
            btn_unbind -> {
                if (service != null) {
                    service = null
                    unbindService(connection)
                }
            }

            btn_get_count -> {
                if (service != null) {
                    // 字符串模板
                    tv.text = """${tv.text.toString()}
${service!!.count.get()}"""
                }
            }
            else -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_service_client)

        tv.text = "BinderServiceClientActivity onCreate"
        btn_start.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
        btn_bind.setOnClickListener(this)
        btn_unbind.setOnClickListener(this)
        btn_get_count.setOnClickListener(this)
    }
}