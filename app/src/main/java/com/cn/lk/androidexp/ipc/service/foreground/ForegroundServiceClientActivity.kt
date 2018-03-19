package com.cn.lk.androidexp.ipc.service.foreground

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.Toast
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_foreground.*
import java.lang.ref.WeakReference


class ForegroundServiceClientActivity : FragmentActivity(), View.OnClickListener, ForegroundCountService.IListener {

    companion object {
        var TAG = "BinderCountService-Client"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground)

        btn_start.setOnClickListener(this)
        btn_set_to.setOnClickListener(this)
        btn_reset.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
    }

    var isFront = false
    override fun onResume() {
        super.onResume()
        isFront = true
    }

    override fun onPause() {
        super.onPause()
        isFront = false
    }

    var service: WeakReference<ForegroundCountService>? = null
    var isBound = false
    var conn: ServiceConnection? = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, ser: IBinder?) {
            service = (ser as ForegroundCountService.ProgressBinder?)?.service
            if (service?.get() != null) {
                service?.get()?.listener = WeakReference(this@ForegroundServiceClientActivity)
                service?.get()?.resume()
                isBound = true
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            service = null
        }

        override fun onBindingDied(name: ComponentName?) {
            isBound = false
            service = null
        }
    }

    override fun onTick(curProgress: Int) {
        if (isFront) {
            tv?.post({
                tv?.text = curProgress.toString()
            })
        }
    }

    override fun onClick(v: View?) {
        when (v) {
        // 开始/继续
            btn_start -> {
                if (!isBound) {
                    val start = Intent(this, ForegroundCountService::class.java)
                    bindService(start, conn, Context.BIND_AUTO_CREATE)
                } else {
                    service?.get()?.resume()
                }
            }
        // 暂停
            btn_pause -> {
                if (isBound) {
                    service?.get()?.pause()
                }
            }
        // 设置为0
            btn_reset -> {
                if (isBound) {
                    service?.get()?.resetProgress(0)
                    tv.text = "0"
                }
            }
        // 设置为50
            btn_set_to -> {
                if (isBound) {
                    service?.get()?.resetProgress(50)
                    tv.text = "50"
                }
            }
            else -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(conn)
            isBound = false
        }

        conn = null
        service = null
    }

}