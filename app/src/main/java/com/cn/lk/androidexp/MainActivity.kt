package com.cn.lk.androidexp

import com.cn.lk.androidexp.ipc.service.ServiceActivity
import com.cn.lk.androidexp.notification.NotificationActivity

class MainActivity : SelectBaseActivity(listOf(
        ActivityItem("Service", ServiceActivity::class.java),
        ActivityItem("Notification", NotificationActivity::class.java)
))
