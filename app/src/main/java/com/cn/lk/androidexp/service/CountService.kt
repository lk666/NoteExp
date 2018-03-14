package com.cn.lk.androidexp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


class CountService : Service() {
    val TAG = "BookService"
    var count = 0
        get() = if (field > -1) field else -1  // get set里用field代替count，此处只为演示
    var stop = false

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    override fun onCreate() {
        super.onCreate()

        stop = false
        count = 0
        Thread(Runnable {
            while (!stop) {
                Thread.sleep(500)
                ++count
            }
        }).start()

        Log.d(TAG, "onCreate")
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param flags 表示启动请求时是否有额外数据
     * ----START_FLAG_REDELIVERY
     * 这个值代表了onStartCommand方法的返回值为START_REDELIVER_INTENT，
     * 而且在上一次服务被杀死前会去调用stopSelf方法停止服务。其中START_REDELIVER_INTENT意味着
     * 当Service因内存不足而被系统kill后，则会重建服务，并通过传递给服务的最后一个 Intent
     * 调用 onStartCommand()，此时Intent是有值的。
     * ----START_FLAG_RETRY
     * 该flag代表当onStartCommand调用后一直没有返回值时，
     * 会尝试重新去调用onStartCommand()。
     *
     * @param startId 指明当前服务的唯一ID，与stopSelfResult(int startId)配合使用，
     * stopSelfResult可以更安全地根据ID停止服务。
     *
     * @return
     * ----START_STICKY
     * 当Service因内存不足而被系统kill后，一段时间后内存再次空闲时，
     * 系统将会尝试重新创建此Service，一旦创建成功后将回调onStartCommand方法，但其中的Intent将是null，
     * 除非有挂起的Intent，如pendingintent，
     * 这个状态下比较适用于不执行命令、但无限期运行并等待作业的媒体播放器或类似服务。
     * ----START_NOT_STICKY
     * 当Service因内存不足而被系统kill后，即使系统内存再次空闲时，系统也不会尝试重新创建此Service。
     * 除非程序中再次调用startService启动此Service，这是最安全的选项，
     * 可以避免在不必要时以及应用能够轻松重启所有未完成的作业时运行服务。
     * ----START_REDELIVER_INTENT
     * 当Service因内存不足而被系统kill后，则会重建服务，
     * 并通过传递给服务的最后一个 Intent 调用 onStartCommand()，任何挂起 Intent均依次传递。
     * 与START_STICKY不同的是，其中的传递的Intent将是非空，是最后一次调用startService中的intent。
     * 这个值适用于主动执行应该立即恢复的作业（例如下载文件）的服务。
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)

    }

    /**
     * 当服务不再使用(全都unbind了)且将被销毁（当使用startService启动，还需调用stopService）时，系统将调用此方法
     */
    override fun onDestroy() {
        super.onDestroy()
        stop = true
        Log.d(TAG, "onDestroy")
    }

    // ==============================================================================

    private var binder:CountBinder? = CountBinder()

    /**
     * 绑定服务时才会调用
     */
    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind")
        return binder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        binder = null
        return super.onUnbind(intent)
    }

    // inner内部类可以引用外部类的成员，例如：成员属性。
    // 不加不是内部类，嵌套类引用不了外部类的成员
    public inner class CountBinder : Binder() {
        public fun getCount() = this@CountService.count // 引用外部类属性
    }
}