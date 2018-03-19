package com.cn.lk.androidexp.ipc.service

import com.cn.lk.androidexp.ActivityItem
import com.cn.lk.androidexp.SelectBaseActivity
import com.cn.lk.androidexp.ipc.service.binder.BinderServiceClientActivity
import com.cn.lk.androidexp.ipc.service.foreground.ForegroundServiceClientActivity
import com.cn.lk.androidexp.ipc.service.messenger.MessengerServiceClientActivity

class ServiceActivity : SelectBaseActivity(listOf(
        // http://blog.csdn.net/javazejian/article/details/52709857
        ActivityItem("Binder扩展式服务", BinderServiceClientActivity::class.java),
        ActivityItem("Messenger式服务", MessengerServiceClientActivity::class.java),
        ActivityItem("前台服务", ForegroundServiceClientActivity::class.java)
))
