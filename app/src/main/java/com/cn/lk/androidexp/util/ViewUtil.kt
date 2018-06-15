package com.cn.lk.androidexp.util

import android.widget.Toast
import com.cn.lk.androidexp.AppContext

/**
 * Created by lk on 2018/6/14.
 */
object ViewUtil {
    fun toast(txt: String?) {
        Toast.makeText(AppContext.instance(), txt, Toast.LENGTH_SHORT).show()
    }
}