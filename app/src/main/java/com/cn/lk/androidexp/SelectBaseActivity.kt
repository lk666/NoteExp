package com.cn.lk.androidexp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class SelectBaseActivity(protected var list: List<ActivityItem>) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL)
        val adapter = ItemAdapter()
        adapter.bindToRecyclerView(rv)
        adapter.replaceData(list)
    }
}
