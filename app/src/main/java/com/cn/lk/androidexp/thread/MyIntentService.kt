package com.cn.lk.androidexp.thread

import android.app.IntentService
import android.content.Intent
import com.orhanobut.logger.Logger

/**
 * 用于单独线程的后台服务，结束后自动退出
 * Created by lk on 2018/7/19.
 */
class MyIntentService:IntentService("MyIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        Thread.sleep(5000)
        Logger.t("MyIntentService").d("MyIntentService--onHandleIntent--${Thread
                .currentThread().id}")
        // 使用广播等与activity等交互
    }

    override fun onCreate() {
        super.onCreate()
        Logger.t("MyIntentService").d("MyIntentService--onCreate--${Thread
                .currentThread().id}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.t("MyIntentService").d("MyIntentService--onDestroy--${Thread
                .currentThread().id}")
    }
}