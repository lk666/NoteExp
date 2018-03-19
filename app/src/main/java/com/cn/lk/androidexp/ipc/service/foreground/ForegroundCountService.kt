package com.cn.lk.androidexp.ipc.service.foreground

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

class ForegroundCountService : Service() {
    companion object {
        var TAG = "BinderCountService"
        /**
         * id不可设置为0,否则不能设置为前台service
         */
        val NOTIFICATION_ID = 0x1
    }

    private var count = AtomicInteger(0)

    /**
     * 获取当前进度
     */
    public fun getProgress() = count.get()

    /**
     * 重新设置进度
     */
    public fun resetProgress(pro: Int) {
        count.set(pro)
    }

    /**
     * 是否正在进行
     */
    private var isOn = AtomicBoolean(false)

    /**
     * 开始
     */
    public fun resume() {
        // 现在没有运行
        if (isOn.compareAndSet(false, true)) {
            thread(start = true) {
                while (isOn.get()) {
                    try {
                        Thread.sleep(1000)
                        listener?.get()?.onTick(count.addAndGet(1))
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    /**
     * 暂停
     */
    public fun pause() {
        isOn.set(false)
    }

    public interface IListener {
        fun onTick(curProgress: Int)
    }

    /**
     * 回调
     */
    public var listener: WeakReference<IListener>? = null

    inner class ProgressBinder(service: ForegroundCountService) : Binder() {
        internal val service = WeakReference(this@ForegroundCountService)
    }

    private var binder: ProgressBinder? = ProgressBinder(this)

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        isOn.set(false)
        binder = null
    }
}