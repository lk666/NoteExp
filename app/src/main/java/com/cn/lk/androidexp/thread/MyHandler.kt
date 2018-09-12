package com.cn.lk.androidexp.thread

import android.app.Activity
import android.os.Handler
import android.os.Message
import android.widget.TextView
import com.cn.lk.androidexp.R
import java.lang.ref.WeakReference

/**
 * 弱引用以不造成内存泄露
 * Created by lk on 2018/7/19.
 */
class MyHandler(activity: Activity):Handler() {
    private val mActivity = WeakReference(activity)
    override fun handleMessage(msg: Message?) {
        if (mActivity.get()==null) {
            return
        }
        val aty = mActivity.get()
        val tv = aty?.findViewById<TextView>(R.id.tv)
        when(msg?.what) {
            0->tv?.text = "${tv?.text}\nHandler(${msg.data["str"]})--${Thread.currentThread()
                    .id}\n"
        }
    }
}