package com.cn.lk.androidexp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

abstract class SelectBaseActivity(protected var list: List<ActivityItem>) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.layoutManager = StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL)
        rv.layoutManager = LinearLayoutManager(this)
        rv.layoutManager = GridLayoutManager(this, 3)
        val adapter = ItemAdapter()
        adapter.bindToRecyclerView(rv)
        adapter.replaceData(list)
    }
}
