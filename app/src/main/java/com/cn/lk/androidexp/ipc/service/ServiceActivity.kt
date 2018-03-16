package com.cn.lk.androidexp.ipc.service

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.cn.lk.androidexp.ActivityItem
import com.cn.lk.androidexp.ItemAdapter
import com.cn.lk.androidexp.R
import com.cn.lk.androidexp.ipc.service.binder.BinderServiceClientActivity
import com.cn.lk.androidexp.ipc.service.foreground.ForegroundServiceClientActivity
import com.cn.lk.androidexp.ipc.service.messenger.MessengerServiceClientActivity

class ServiceActivity : AppCompatActivity() {
    private val list = listOf<ActivityItem>(
            // http://blog.csdn.net/javazejian/article/details/52709857
            ActivityItem("Binder扩展式服务",  BinderServiceClientActivity::class.java),
            ActivityItem("Messenger式服务",  MessengerServiceClientActivity::class.java),
            ActivityItem("前台服务",  ForegroundServiceClientActivity::class.java)
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL)
        var adapter = ItemAdapter()
        adapter.bindToRecyclerView(rv)
        adapter.replaceData(list)
    }
}
