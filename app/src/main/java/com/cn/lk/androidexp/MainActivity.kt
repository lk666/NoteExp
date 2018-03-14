package com.cn.lk.androidexp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.cn.lk.androidexp.ipc.service.binder.BinderServiceClientActivity

class MainActivity : AppCompatActivity() {
    private val list = listOf<ActivityItem>(ActivityItem("Service",
            BinderServiceClientActivity::class.java))

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
