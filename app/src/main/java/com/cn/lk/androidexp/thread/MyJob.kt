package com.cn.lk.androidexp.thread

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.orhanobut.logger.Logger

/**
 * 构造函数指定优先级
 * Created by lk on 2018/7/20.
 */
class MyJob:Job(
        Params(1)
                // requireNetwork，需要网络连接
                // persist，需要持久化
//                .requireNetwork().persist()
) {

    override fun onAdded() {
         // Job已经被保存到磁盘里(persist时)，可以用来更新UI
    }

    override fun onRun() {
        // 在这里处理Job逻辑，例如网络请求等，所有的工作就是异步完成
        Thread.sleep(5500)
        Logger.t("MyJob").d("MyJob--onRun--${Thread.currentThread().id}")
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        // 在onRun里发生异常处理
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }


    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        // Job被取消时调用
    }
}