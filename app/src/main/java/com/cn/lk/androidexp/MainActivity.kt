package com.cn.lk.androidexp

import com.cn.lk.androidexp.annotation.SupportAnnotationActivity
import com.cn.lk.androidexp.ipc.service.ServiceActivity
import com.cn.lk.androidexp.kotlin.KotlinActivity
import com.cn.lk.androidexp.notification.NotificationActivity
import com.cn.lk.androidexp.thread.ThreadActivity

class MainActivity : SelectBaseActivity(listOf(
        ActivityItem("Service", ServiceActivity::class.java),
        ActivityItem("Notification", NotificationActivity::class.java),
        ActivityItem("Kotlin", KotlinActivity::class.java),
        ActivityItem("Annotataion", SupportAnnotationActivity::class.java),
        ActivityItem("Thread", ThreadActivity::class.java)

))
