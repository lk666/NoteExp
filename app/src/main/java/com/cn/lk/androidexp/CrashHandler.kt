package com.cn.lk.androidexp

import com.orhanobut.logger.Logger

/**
 * 异常信息捕获
 * Created by lk on 2018/7/2.
 */
class CrashHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        // 回调函数，处理异常出现后的情况
        Logger.t("uncaughtException").e(e?.message!!)
    }
}