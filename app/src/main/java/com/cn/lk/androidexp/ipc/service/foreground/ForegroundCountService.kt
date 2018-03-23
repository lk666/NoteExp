package com.cn.lk.androidexp.ipc.service.foreground

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.cn.lk.androidexp.R
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
        val NOTIFICATION_ID = 1
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
        startForeground(NOTIFICATION_ID, createCustomNotify())
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        isOn.set(false)
        binder = null
        stopForeground(true)
    }

    /**
     * 自定义通知布局的可用高度取决于通知视图。普通视图布局限制为 64 dp，扩展视图布局限制为 256 dp
     * 使用RemoteViews
     */
    private fun createCustomNotify(): Notification {
        val builder = NotificationCompat.Builder(this)

        // 自定义的话，这些设置中，关于界面的设置全都没卵用
        builder
                // 小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                // 标题
                .setContentTitle("标题")
                // 正文
                .setContentText("正文")
                // 摘要
                .setSubText("摘要")
                // 点击后是否自动取消（false：点击不取消）
                .setAutoCancel(false)
                // 设置优先级
                .setPriority(NotificationCompat.PRIORITY_MAX)
                // 自定义消息时间，以毫秒为单位，当前设置为比系统时间少一小时
                .setWhen(System.currentTimeMillis() - 3600000)
                // 设置为一个正在进行的通知，此时用户无法侧滑清除通知（true：不能侧滑删除）
                .setOngoing(true)
                // 设置消息的提醒方式，震动提醒：DEFAULT_VIBRATE     声音提醒：NotificationCompat.DEFAULT_SOUND
                // 三色灯提醒NotificationCompat.DEFAULT_LIGHTS     以上三种方式一起：DEFAULT_ALL
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // 设置震动方式，延迟零秒，震动一秒，延迟一秒、震动一秒
                .setVibrate(longArrayOf(0, 1000, 1000, 1000))

                //---------------------- 没试出效果
                // 显示指定文本
                .setContentInfo("Info")
                // 与setContentInfo类似，但如果设置了setContentInfo则无效果
                // 用于当显示了多个相同ID的Notification时，显示消息总数
                .setNumber(2)
                // 通知在状态栏显示时的文本
                .setTicker("在状态栏上显示的文本")


        // 设置点击通知后的动作
        val lastIntent = Intent(this, ForegroundServiceClientActivity::class.java)
        val ptIntent = PendingIntent.getActivity(this, 0, lastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(ptIntent)

        ///////////////////// 自定义布局，不能使用自定义VIew，RemoteViews只支持部分View：
//        layout：
//        FrameLayout  LinearLayout  RelativeLayout  GridLayout
//        View：
//        AnalogClock, button, Chronometer, ImageButton, ImageView ,ProgressBar ,TextView,
//        ViewFlipper, ListView , GridView ,StackView,AdapterViewFlipper,ViewStub;

        val remoteView = RemoteViews(application.packageName, R.layout.view_foreground_remote)
//        val startIntent = Intent(this, ForegroundCountService::class.java)
//        val pStartIntent = PendingIntent.getBroadcast(this, 0, startIntent, PendingIntent
//                .FLAG_UPDATE_CURRENT)
//        remoteView.setOnClickPendingIntent(R.id.btn_start, pStartIntent)
//        todo 使用广播，实现控制

//        builder.setContent(remoteView)
        builder.setCustomBigContentView(remoteView)

        return builder.build()
    }
}