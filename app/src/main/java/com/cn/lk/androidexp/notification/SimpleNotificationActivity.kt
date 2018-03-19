package com.cn.lk.androidexp.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_simple_notify.*

// https://www.jianshu.com/p/ec67ba83934a
/**
 * 简单通知
 */
class SimpleNotificationActivity : FragmentActivity(), View.OnClickListener {

    companion object {
        var TAG = "SimpleNotificationActivity-Client"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_notify)

        btn_notify.setOnClickListener(this)
        btn_clear_last.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)
    }

    var notify: Notification? = null

    override fun onClick(v: View?) {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        when (v) {
        // 通知
            btn_notify -> {
                notify = createNotify(curId)
                nm.notify(curId++, notify)
            }
        // 清除全部
            btn_clear -> {
                nm.cancelAll()
                curId = 1
            }
        // 清除上一个
            btn_clear_last -> {
                if (curId > 1) {
                    nm.cancel(--curId)
                }
            }
        // 更新上一个内容
            btn_refresh -> {
                notify = createNotify(-1)
                nm.notify(curId - 1, notify)
            }
            else -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    var curId = 1

    private fun createNotify(type: Int): Notification {
        val builder = NotificationCompat.Builder(this)
        val bm = (ContextCompat.getDrawable(this, R.mipmap.ic_launcher) as BitmapDrawable).bitmap
        builder
                // 小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                // 大图标
                .setLargeIcon(bm)
                // 标题
                .setContentTitle("标题")
                // 正文
                .setContentText("正文, id = $curId")
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
        val intent = Intent(this, SimpleNotificationActivity::class.java)
        var ptIntent = PendingIntent.getActivity(this, 0, intent, 0)

        // 设置回退栈
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
           val stackBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
               TaskStackBuilder.create(this)
           } else {
              null
           }
           stackBuilder?.addParentStack(NotificationActivity::class.java)
           stackBuilder?.addNextIntent(intent)
           ptIntent =  stackBuilder?.getPendingIntent(0, PendingIntent.FLAG_NO_CREATE)
        }

        builder.setContentIntent(ptIntent)

        when ((type - 1) % 4) {
            0 -> {
                ///////////// 长文本样式
                val bs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    NotificationCompat.BigTextStyle()
                } else {
                    null
                }

                if (bs != null) {
                    bs.bigText("""超长文本有高度限制的
                        超超超超超超超超超超超超超超
                        超超超超超超超超超超超超超
                        超超超超超超超超超超超超
                        超超超超超超超超超超超
                        超超超超超超超超超超
                        超超超超超超超超超
                        超超超超超超超超
                        超超超超超超超
                        超超超超超超
                        超超超超超
                        超超超超
                        超超超
                        超超
                        超
                        长文本""".trimIndent())
                    bs.setBigContentTitle("点击后长文本的标题")
                    bs.setSummaryText("点击后长文本的摘要")
                    builder.setStyle(bs)
                }
            }
            1 -> {
                ///////////////  大图样式
                val style = NotificationCompat.BigPictureStyle()
                style.setBigContentTitle("展开后的标题")
                style.setSummaryText("这是摘要")
                style.bigPicture(bm)
                builder.setStyle(style)
            }
            2 -> {
                ////////////// 扩展（列表）布局
                val inboxStyle = NotificationCompat.InboxStyle()
                val events = listOf("扩展（列表）布局有高度限制", "第二", "第三", "第四", "第五", "第六", "第七", "第一", "第二", "第三",
                        "第四", "第五",
                        "第六", "第七", "第一", "第二", "第三", "第四", "第五", "第六", "第七")
                inboxStyle.setBigContentTitle("展开后的标题")
                inboxStyle.setSummaryText("这是摘要")
                for (event in events) {
                    inboxStyle.addLine(event)
                }
                builder.setStyle(inboxStyle)
            }
            3 -> {
                ////////////// 扩展（列表）布局
                val inboxStyle = NotificationCompat.InboxStyle()
                val events = listOf("扩展（列表）布局有高度限制", "第二", "第三", "第四", "第五", "第六", "第七", "第一", "第二", "第三",
                        "第四", "第五",
                        "第六", "第七", "第一", "第二", "第三", "第四", "第五", "第六", "第七")
                inboxStyle.setBigContentTitle("展开后的标题")
                inboxStyle.setSummaryText("这是摘要")
                for (event in events) {
                    inboxStyle.addLine(event)
                }
                builder.setStyle(inboxStyle)
            }
            else -> {
                // 默认样式
                builder.setContentTitle("修改后的内容")
            }
        }

        return builder.build()
    }
}