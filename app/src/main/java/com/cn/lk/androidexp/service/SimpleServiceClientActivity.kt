package com.cn.lk.androidexp.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_simple_service_client.*


class SimpleServiceClientActivity : FragmentActivity(), View.OnClickListener {

    var TAG = "BookService-Client"

    var binder: CountService.CountBinder? = null

    private val connection = object : ServiceConnection {
        /**
         * Android 系统会在与服务的连接意外中断时（例如当服务崩溃或被终止时）调用该方法。
         * 注意:当客户端取消绑定时，系统“绝对不会”调用该方法。
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
            binder = null
            isBind = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            binder = (service ?: return) as CountService.CountBinder?
        }

        override fun onBindingDied(name: ComponentName?) {
//            super.onBindingDied(name)
            Log.d(TAG, "onBindingDied")
            binder = null
            isBind = false
        }
    }

    var isBind = false

    override fun onClick(v: View?) {
        var intent = Intent(this, CountService::class.java)

        when (v) {
            btn_start -> startService(intent)
            btn_stop -> stopService(intent)

        // flags则是指定绑定时是否自动创建Service。0代表不自动创建、BIND_AUTO_CREATE则代表自动创建。
            btn_bind -> {
                isBind = bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
            btn_unbind -> {
                if (isBind) {
                    unbindService(connection)
//                    binder = null
                    isBind = false
                }
            }

            btn_get_count -> tv.text = tv.text.toString() + "\n" + binder?.getCount()
            else -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }

        var k = 0
        var a : Float = k.toFloat()
        while (a < 1000) {
            TextUtils.isEmpty("asdsad")
            a++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_service_client)

        tv.text = "SimpleServiceClientActivity onCreate"
        btn_start.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
        btn_bind.setOnClickListener(this)
        btn_unbind.setOnClickListener(this)
        btn_get_count.setOnClickListener(this)
    }
}