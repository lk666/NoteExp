package com.cn.lk.androidexp.notification

import com.cn.lk.androidexp.ActivityItem
import com.cn.lk.androidexp.SelectBaseActivity

class NotificationActivity : SelectBaseActivity(listOf(
//            ActivityItem("Binder扩展式服务",  BinderServiceClientActivity::class.java),
//            ActivityItem("Messenger式服务",  MessengerServiceClientActivity::class.java),
        ActivityItem("简单通知", SimpleNotificationActivity::class.java)
))
